package overflow.rebridge.domain.trainingprogram;

import jakarta.persistence.*;
import overflow.rebridge.domain.checklist.CheckList;

@Entity
@Table(name = "training_program")
public class TrainingProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_program_id")
    private Long studyProgramId;

    @OneToOne
    @JoinColumn(name = "check_list_id")
    private CheckList checkList;

    private String resettlementSupport;
    private String foreignWorkerTraining;
}
