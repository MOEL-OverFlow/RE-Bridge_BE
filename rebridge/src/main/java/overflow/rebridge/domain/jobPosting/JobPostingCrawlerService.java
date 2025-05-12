package overflow.rebridge.domain.jobPosting;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.util.Pair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import overflow.rebridge.domain.member.Nation;

@Service
@RequiredArgsConstructor
public class JobPostingCrawlerService {

    private final JobPostingRepository jobPostingRepository;
    private static final String BASE_URL = "https://eps.hrdkorea.or.kr/e9/user/jobMatching/jobRecruit.do?method=recruitList&currentPage=";

    @Scheduled(cron = "0 0 3 * * ?") // 매일 새벽 3시
    @Transactional
    public void crawl() {
        int totalPages = getTotalPages();

        for (int page = 1; page <= totalPages; page++) {
            try {
                Document doc = Jsoup.connect(BASE_URL + page).get();
                Elements rows = doc.select(".tbl01 tbody tr");

                for (Element row : rows) {
                    Elements tds = row.select("td");
                    if (tds.size() < 9) continue;

                    String companyName = tds.get(1).text().trim();
                    String jobTitle = tds.get(2).text().trim();
                    String industryStr = tds.get(3).text().trim();
                    String nationStr = tds.get(4).text().trim();
                    String recruitmentStr = tds.get(5).text().replaceAll("[^0-9]", "0").trim();
                    String experienceStr = tds.get(6).text().trim();
                    String koreanSkillStr = tds.get(7).text().trim();
                    String deadline = tds.get(8).text().trim();

                    Element link = tds.get(1).selectFirst("a");
                    String detailUrl = link != null ? link.absUrl("href") : "";

                    if (jobPostingRepository.existsByDetailUrl(detailUrl)) {
                        continue;
                    }

                    Pair<Field, JobType> fieldAndJobType = mapFieldAndJobType(jobTitle);

                    JobPosting post = JobPosting.of(
                            companyName,
                            detailUrl,
                            mapIndustry(industryStr),
                            mapNation(nationStr),
                            Integer.parseInt(recruitmentStr),
                            mapExperience(experienceStr),
                            mapKoreanSkill(koreanSkillStr),
                            deadline,
                            fieldAndJobType.getFirst(),
                            fieldAndJobType.getSecond()
                    );

                    jobPostingRepository.save(post);
                }

            } catch (Exception e) {
                System.err.println("❌ [페이지 " + page + "] 크롤링 실패: " + e.getMessage());
            }
        }

        System.out.println("✅ 채용공고 크롤링 완료");
    }

    private int getTotalPages() {
        try {
            Document doc = Jsoup.connect(BASE_URL + "1").get();
            Element resultText = doc.selectFirst("div.total span"); // 예: "총 505 건"

            if (resultText != null) {
                String text = resultText.text().replaceAll("[^0-9]", ""); // "505"
                int total = Integer.parseInt(text);
                return (total + 9) / 10; // 10개씩 페이지 나눔
            }
        } catch (Exception e) {
            System.err.println("❌ 총 건수 파싱 실패: " + e.getMessage());
        }

        return 1; // 실패 시 fallback
    }

    private Pair<Field, JobType> mapFieldAndJobType(String text) {
        String[] parts = text.split("/");
        String fieldStr = parts.length > 0 ? parts[0].trim() : "";
        String jobTypeStr = parts.length > 1 ? parts[1].trim() : "";

        Field field = switch (fieldStr) {
            case "Construction 건설" -> Field.CONSTRUCTION;
            case "Metal 금속" -> Field.METAL;
            case "Machine 기계" -> Field.MACHINE;
            case "Electricity 전기" -> Field.ELECTRICITY;
            case "Electronic 전자" -> Field.ELECTRONIC;
            case "Telecommunications 통신" -> Field.TELECOMMUNICATIONS;
            case "Textile 섬유" -> Field.TEXTILE;
            case "Chemicals 화학" -> Field.CHEMICALS;
            case "Food 식품" -> Field.FOOD;
            case "Agriculture 농업" -> Field.AGRICULTURE;
            case "Stockbreeding 축산" -> Field.STOCKBREEDING;
            case "Fishery 어업" -> Field.FISHERY;
            case "Woodwork 목재" -> Field.WOODWORK;
            case "Transport 운송" -> Field.TRANSPORT;
            default -> Field.NONE;
        };

        JobType jobType = switch (jobTypeStr) {
            case "Production Management 생산관리직" -> JobType.PRODUCTION_MANAGEMENT;
            case "Business Management 영업관리직" -> JobType.BUSINESS_MANAGEMENT;
            case "Interpret 통역" -> JobType.INTERPRET;
            case "Clerical work 사무직" -> JobType.CLERICAL_WORK;
            default -> JobType.NONE;
        };

        return Pair.of(field, jobType);
    }

    private IndustryType mapIndustry(String text) {
        return switch (text.trim()) {
            case "농업·임업 및 어업" -> IndustryType.AGRICULTURE_FORESTRY_FISHERY;
            case "광업" -> IndustryType.MINING;
            case "제조업" -> IndustryType.MANUFACTURING;
            case "전기·가스·증기 및 수도사업" -> IndustryType.ELECTRICITY_GAS_WATER;
            case "하수·폐기물 처리·원료재생 및 환경복원업" -> IndustryType.WASTE_ENVIRONMENT;
            case "건설업" -> IndustryType.CONSTRUCTION;
            case "도매 및 소매업" -> IndustryType.WHOLESALE_RETAIL;
            case "운수업" -> IndustryType.TRANSPORTATION;
            case "숙박 및 음식점업" -> IndustryType.ACCOMMODATION_FOOD;
            case "출판·영상·방송통신 및 정보서비스업" -> IndustryType.MEDIA_COMMUNICATION;
            case "금융 및 보험업" -> IndustryType.FINANCE_INSURANCE;
            case "부동산업 및 임대업" -> IndustryType.REAL_ESTATE;
            case "전문·과학 및 기술 서비스업" -> IndustryType.SCIENCE_TECH;
            case "사업시설관리 및 사업지원 서비스업" -> IndustryType.BUSINESS_SUPPORT;
            case "공공행정·국방 및 사회보장 행정" -> IndustryType.PUBLIC_ADMINISTRATION;
            case "교육 서비스업" -> IndustryType.EDUCATION;
            case "보건업 및 사회복지 서비스업" -> IndustryType.HEALTH_SOCIAL_WORK;
            case "예술·스포츠 및 여가관련 서비스업" -> IndustryType.ARTS_SPORTS;
            case "협회 및 단체·수리 및 기타 개인 서비스업" -> IndustryType.ASSOCIATIONS_PERSONAL_SERVICES;
            case "가구 내 고용활동 및 달리 분류되지 않은 자가소비 생산활동" -> IndustryType.HOUSEHOLD_SELF_PRODUCTION;
            case "국제 및 외국기관" -> IndustryType.INTERNATIONAL_ORGANIZATIONS;
            default -> IndustryType.NONE;
        };
    }

    private Nation mapNation(String text) {
        return switch (text) {
            case "베트남" -> Nation.VIETNAM;
            case "네팔" -> Nation.NEPAL;
            case "미얀마" -> Nation.MYANMAR;
            case "캄보디아" -> Nation.CAMBODIA;
            case "중국" -> Nation.CHINA;
            case "스리랑카" -> Nation.SRI_LANKA;
            case "우즈베키스탄" -> Nation.UZBEKISTAN;
            case "키르기스스탄" -> Nation.KYRGYZ;
            case "라오스" -> Nation.LAOS;
            case "몽골" -> Nation.MONGOLIA;
            case "방글라데시" -> Nation.BANGLADESH;
            case "필리핀" -> Nation.PHILIPPINES;
            case "태국" -> Nation.THAILAND;
            case "파키스탄" -> Nation.PAKISTAN;
            case "동티모르" -> Nation.TIMOR_LESTE;
            case "인도네시아" -> Nation.INDONESIA;
            default -> Nation.OTHER;
        };
    }

    private ExperienceType mapExperience(String text) {
        return switch (text) {
            case "신입" -> ExperienceType.ENTRY;
            case "경력" -> ExperienceType.EXPERIENCED;
            default -> ExperienceType.NONE;
        };
    }

    private KoreanSkillLevel mapKoreanSkill(String text) {
        return switch (text) {
            case "상" -> KoreanSkillLevel.HIGH;
            case "중" -> KoreanSkillLevel.MEDIUM;
            case "하" -> KoreanSkillLevel.LOW;
            default -> KoreanSkillLevel.NONE;
        };
    }
}
