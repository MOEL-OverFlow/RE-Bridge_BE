package overflow.rebridge.domain.checklist;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import overflow.rebridge.domain.document.Document;
import overflow.rebridge.domain.educationprogram.EducationProgram;
import overflow.rebridge.domain.insurance.Insurance;
import overflow.rebridge.domain.member.Member;

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
    private EducationProgram educationProgram;

    public CheckList() {}

    // 체크리스트 생성용 생성자
    public CheckList(Member member) {
        this.member = member;

        // 하위 엔티티들 생성 + 연결
        this.document = new Document(this);
        this.insurance = new Insurance(this);
        this.educationProgram = new EducationProgram(this);
    }
}
