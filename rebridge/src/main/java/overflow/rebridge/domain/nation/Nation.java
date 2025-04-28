package overflow.rebridge.domain.nation;

import jakarta.persistence.*;
import overflow.rebridge.domain.company.Company;
import overflow.rebridge.domain.member.Member;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "nation")
public class Nation {
    @Id
    @Column(name = "nation_id")
    private String nationCode;

    @Column(name = "nation_name", nullable = false)
    private String nationName;

    @Column(name = "nation_phone", nullable = false)
    private String phoneCode;

    @OneToMany(mappedBy = "nation")
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "nation")
    private List<Company> companies = new ArrayList<>();
}
