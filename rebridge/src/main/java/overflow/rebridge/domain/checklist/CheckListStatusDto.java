package overflow.rebridge.domain.checklist;

public record CheckListStatusDto(
        // Document 상태
        boolean customDeclaration,
        boolean severancePay,

        // Insurance 상태
        boolean departureInsurance,
        boolean expenseInsurance,
        boolean suretyInsurance,
        boolean accidentInsurance,

        // Training Program 상태
        boolean resettlementSupport,
        boolean foreignWorkerTraining
) {}