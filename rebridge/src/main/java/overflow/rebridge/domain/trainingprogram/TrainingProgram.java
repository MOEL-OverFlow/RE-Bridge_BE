package overflow.rebridge.domain.trainingprogram;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import overflow.rebridge.domain.checklist.CheckList;

@Entity
@Table(name = "training_program")
@Getter
@Setter
public class TrainingProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "training_program_id")
    private Long trainingProgramId;

    @OneToOne
    @JoinColumn(name = "checklist_id")
    private CheckList checkList;

    private boolean resettlementSupport;
    private boolean foreignWorkerTraining;
}
