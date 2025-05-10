package overflow.rebridge.domain.checklist;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import overflow.rebridge.domain.document.Document;
import overflow.rebridge.domain.document.DocumentRepository;
import overflow.rebridge.domain.insurance.Insurance;
import overflow.rebridge.domain.insurance.InsuranceRepository;
import overflow.rebridge.domain.insurance.InsuranceService;
import overflow.rebridge.domain.member.MemberService;
import overflow.rebridge.domain.trainingprogram.TrainingProgram;
import overflow.rebridge.domain.trainingprogram.TrainingProgramRepository;

@Service
@RequiredArgsConstructor
public class CheckListService {

    private final CheckListRepository checkListRepository;

//    public CheckList getCheckListByMemberId(Long memberId) {
//        return checkListRepository.findByMemberId(memberId)
//                .orElseThrow(() -> new EntityNotFoundException("CheckList not found for member : " + memberId));
//    }

    public CheckListStatusResponse getStatus(Long memberId) {
        CheckList checkList = checkListRepository.findByMemberId(memberId)
                .orElseThrow(() -> new EntityNotFoundException("CheckList not found for member id: " + memberId));

        Document document = checkList.getDocument();
        Insurance insurance = checkList.getInsurance();
        TrainingProgram trainingProgram = checkList.getTrainingProgram();

        return new CheckListStatusResponse(
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


}
