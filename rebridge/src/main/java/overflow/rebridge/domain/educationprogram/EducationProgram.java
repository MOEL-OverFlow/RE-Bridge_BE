package overflow.rebridge.domain.educationprogram;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import overflow.rebridge.domain.checklist.CheckList;

@Entity
@Table(name = "education_program")
@Getter
@Setter
public class EducationProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_program_id")
    private Long educationProgramId;

    @OneToOne
    @JoinColumn(name = "checklist_id")
    private CheckList checkList;

    private boolean repatriationSupport;
    private boolean foreignWorkerTraining;
}
