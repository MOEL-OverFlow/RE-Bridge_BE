package overflow.rebridge.domain.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import overflow.rebridge.domain.industrycategory.IndustryCategory;
import overflow.rebridge.domain.nation.Nation;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    //국가별 채용 공고 찾기
    List<Company> findByNation(Nation nation);

    //산업별 채용 공고 찾기
    List<Company> findByIndustryCategory(IndustryCategory industryCategory);
}
