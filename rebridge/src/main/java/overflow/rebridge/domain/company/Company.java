package overflow.rebridge.domain.company;

import jakarta.persistence.*;
import overflow.rebridge.domain.industrycategory.IndustryCategory;
import overflow.rebridge.domain.nation.Nation;

@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long companyId;

    @ManyToOne
    @JoinColumn(name = "nation_code")
    private Nation nation;

    @ManyToOne
    @JoinColumn(name = "industry_company_id")
    private IndustryCategory industryCategory;

    private String city;
    private String address;
    private String establishmentYear;
    private String companyName;
    private String companyEmail;
    private String companyUrl;
    private String companyTel;
}
