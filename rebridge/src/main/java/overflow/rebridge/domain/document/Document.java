package overflow.rebridge.domain.document;

import jakarta.persistence.*;
import overflow.rebridge.domain.checklist.CheckList;

@Entity
@Table(name = "document")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private Long documentId;

    @OneToOne
    @JoinColumn(name = "check_list_id")
    private CheckList checkList;

    private boolean customDeclaration;
    private boolean severancePay;
}
