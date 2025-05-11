package overflow.rebridge.domain.checklist;

public record CheckListStatusDto(
        // Document 상태
        boolean customDeclaration,
        boolean retirementAllowance,

        // Insurance 상태
        boolean departureInsurance,
        boolean expenseInsurance,
        boolean suretyInsurance,
        boolean accidentInsurance,

        // Education Program 상태
        boolean repatriationSupport,
        boolean foreignWorkerTraining
) {}