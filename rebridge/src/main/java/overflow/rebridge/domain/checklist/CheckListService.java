package overflow.rebridge.domain.checklist;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import overflow.rebridge.domain.document.Document;
import overflow.rebridge.domain.educationprogram.EducationProgram;
import overflow.rebridge.domain.insurance.Insurance;

@Service
@RequiredArgsConstructor
public class CheckListService {

    private final CheckListRepository checkListRepository;

    @Cacheable(cacheNames = "checklist", key = "#memberId")
    public CheckListStatusDto getStatus(Long memberId) {
        CheckList checkList = checkListRepository.findByMemberMemberId(memberId)
                .orElseThrow(() -> new EntityNotFoundException("CheckList not found for member id: " + memberId));

        Document document = checkList.getDocument();
        Insurance insurance = checkList.getInsurance();
        EducationProgram educationProgram = checkList.getEducationProgram();

        return new CheckListStatusDto(
                document.isCustomDeclaration(),
                document.isRetirementAllowance(),
                insurance.isDepartureInsurance(),
                insurance.isExpenseInsurance(),
                insurance.isSuretyInsurance(),
                insurance.isAccidentInsurance(),
                educationProgram.isRepatriationSupport(),
                educationProgram.isForeignWorkerTraining()
        );
    }

    @Transactional
    @Cacheable(cacheNames = "checklist", key = "#memberId")
    public String setStatus(Long memberId, CheckListStatusDto dto) {
        CheckList checkList = checkListRepository.findByMemberMemberId(memberId)
                .orElseThrow(() -> new EntityNotFoundException("CheckList not found for member id: " + memberId));

        Document document = checkList.getDocument();
        Insurance insurance = checkList.getInsurance();
        EducationProgram educationProgram = checkList.getEducationProgram();

        // Document 업데이트
        document.setCustomDeclaration(dto.customDeclaration());
        document.setRetirementAllowance(dto.retirementAllowance());

        // Insurance 업데이트
        insurance.setDepartureInsurance(dto.departureInsurance());
        insurance.setExpenseInsurance(dto.expenseInsurance());
        insurance.setSuretyInsurance(dto.suretyInsurance());
        insurance.setAccidentInsurance(dto.accidentInsurance());

        // EducationProgram 업데이트
        educationProgram.setRepatriationSupport(dto.repatriationSupport());
        educationProgram.setForeignWorkerTraining(dto.foreignWorkerTraining());

        checkListRepository.save(checkList);

        return "Completed to update checklist";
    }

}
