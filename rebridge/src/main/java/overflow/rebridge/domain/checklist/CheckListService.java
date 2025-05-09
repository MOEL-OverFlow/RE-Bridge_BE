package overflow.rebridge.domain.checklist;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import overflow.rebridge.domain.document.DocumentRepository;
import overflow.rebridge.domain.insurance.Insurance;
import overflow.rebridge.domain.insurance.InsuranceRepository;
import overflow.rebridge.domain.insurance.InsuranceService;
import overflow.rebridge.domain.member.MemberService;
import overflow.rebridge.domain.trainingprogram.TrainingProgramRepository;

@Service
public class CheckListService {

    @Autowired
    private CheckListRepository checkListRepository;

    public CheckList getCheckListByMemberId(Long memberId) {
        return checkListRepository.findByMemberId(memberId)
                .orElseThrow(() -> new EntityNotFoundException("CheckList not found for member : " + memberId));
    }
}
