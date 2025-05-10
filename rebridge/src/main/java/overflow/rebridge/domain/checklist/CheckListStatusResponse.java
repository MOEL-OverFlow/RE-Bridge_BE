package overflow.rebridge.domain.checklist;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckListStatusResponse {

    //Document 상태
    private boolean customDeclaration;
    private boolean severancePay;

    //Insurance 상태
    private boolean departureInsurance;
    private boolean expenseInsurance;
    private boolean suretyInsurance;
    private boolean accidentInsurance;

    //Training Program 상태
    private boolean resettlementSupport;
    private boolean foreignWorkerTraining;
}
