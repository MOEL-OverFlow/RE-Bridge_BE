package overflow.rebridge.domain.industrycategory;

import jakarta.persistence.*;
import overflow.rebridge.domain.company.Company;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "industry_category")
public class IndustryCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "industry_category_id")
    private String industryId;

    @Column(name = "category_name")
    private String categoryName;

    @OneToMany(mappedBy = "industryCategory")
    private List<Company> companies = new ArrayList<>();
}
