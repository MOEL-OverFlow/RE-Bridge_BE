package overflow.rebridge.domain.checklist;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import overflow.rebridge.domain.document.Document;
import overflow.rebridge.domain.insurance.Insurance;
import overflow.rebridge.domain.member.Member;
import overflow.rebridge.domain.trainingprogram.TrainingProgram;

@Entity
@Getter
@Setter
@Table(name = "checklist")
public class CheckList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checklist_id")
    private Long checkListId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(mappedBy = "checkList", cascade = CascadeType.ALL)
    private Insurance insurance;

    @OneToOne(mappedBy = "checkList", cascade = CascadeType.ALL)
    private Document document;

    @OneToOne(mappedBy = "checkList", cascade = CascadeType.ALL)
    private TrainingProgram trainingProgram;
}
