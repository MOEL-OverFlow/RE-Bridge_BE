package overflow.rebridge.domain.document;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import overflow.rebridge.domain.checklist.CheckList;

@Entity
@Table(name = "document")
@Getter
@Setter
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private Long documentId;

    @OneToOne
    @JoinColumn(name = "checklist_id")
    private CheckList checkList;

    private boolean customDeclaration;
    private boolean severancePay;
}
