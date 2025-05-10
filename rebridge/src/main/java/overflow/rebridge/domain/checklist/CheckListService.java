package overflow.rebridge.domain.checklist;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import overflow.rebridge.domain.document.Document;
import overflow.rebridge.domain.insurance.Insurance;
import overflow.rebridge.domain.trainingprogram.TrainingProgram;

@Service
@RequiredArgsConstructor
public class CheckListService {

    private final CheckListRepository checkListRepository;

    @Cacheable(cacheNames = "checklist", key = "#memberId")
    public CheckListStatusDto getStatus(Long memberId) {
        CheckList checkList = checkListRepository.findByMemberId(memberId)
                .orElseThrow(() -> new EntityNotFoundException("CheckList not found for member id: " + memberId));

        Document document = checkList.getDocument();
        Insurance insurance = checkList.getInsurance();
        TrainingProgram trainingProgram = checkList.getTrainingProgram();

        return new CheckListStatusDto(
                document.isCustomDeclaration(),
                document.isSeverancePay(),
                insurance.isDepartureInsurance(),
                insurance.isExpenseInsurance(),
                insurance.isSuretyInsurance(),
                insurance.isAccidentInsurance(),
                trainingProgram.isResettlementSupport(),
                trainingProgram.isForeignWorkerTraining()
        );
    }

    @Transactional
    @Cacheable(cacheNames = "checklist", key = "#memberId")
    public String setStatus(Long memberId, CheckListStatusDto dto) {
        CheckList checkList = checkListRepository.findByMemberId(memberId)
                .orElseThrow(() -> new EntityNotFoundException("CheckList not found for member id: " + memberId));

        Document document = checkList.getDocument();
        Insurance insurance = checkList.getInsurance();
        TrainingProgram trainingProgram = checkList.getTrainingProgram();

        // Document 업데이트
        document.setCustomDeclaration(dto.customDeclaration());
        document.setSeverancePay(dto.severancePay());

        // Insurance 업데이트
        insurance.setDepartureInsurance(dto.departureInsurance());
        insurance.setExpenseInsurance(dto.expenseInsurance());
        insurance.setSuretyInsurance(dto.suretyInsurance());
        insurance.setAccidentInsurance(dto.accidentInsurance());

        // TrainingProgram 업데이트
        trainingProgram.setResettlementSupport(dto.resettlementSupport());
        trainingProgram.setForeignWorkerTraining(dto.foreignWorkerTraining());

        checkListRepository.save(checkList);

        return "Completed to update checklist";
    }

}
