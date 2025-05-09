package overflow.rebridge.domain.insurance;

import jakarta.persistence.*;
import overflow.rebridge.domain.checklist.CheckList;

@Entity
@Table(name = "insurance")
public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "insurance_id")
    private Long insuranceId;

    @OneToOne
    @JoinColumn(name = "check_list_id")
    private CheckList checkList;

    private boolean departureInsurance;
    private boolean expenseInsurance;
    private boolean suretyInsurance;
    private boolean accidentInsurance;
}
