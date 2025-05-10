package overflow.rebridge.domain.insurance;

import jakarta.persistence.*;
import lombok.Getter;
import overflow.rebridge.domain.checklist.CheckList;

@Entity
@Table(name = "insurance")
@Getter
public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "insurance_id")
    private Long insuranceId;

    @OneToOne
    @JoinColumn(name = "checklist_id")
    private CheckList checkList;

    private boolean departureInsurance;
    private boolean expenseInsurance;
    private boolean suretyInsurance;
    private boolean accidentInsurance;
}
