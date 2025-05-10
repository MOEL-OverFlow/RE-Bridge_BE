package overflow.rebridge.domain.member;

import jakarta.persistence.*;
import lombok.Getter;
import overflow.rebridge.domain.nation.Nation;
import overflow.rebridge.domain.checklist.CheckList;
import overflow.rebridge.domain.image.Image;
import overflow.rebridge.domain.interest.Interest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "member_name", nullable = false)
    private String name;

    @Column(name = "member_email", nullable = false)
    private String email;

    @Column(name = "member_password", nullable = false)
    private String password;

    @Column(name = "birthDate", nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;  // 🔹 Role 필드 추가

    @ManyToOne
    @JoinColumn(name = "nation_id")
    private Nation nation;

    @OneToMany(mappedBy = "member")
    private List<Interest> interest = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<CheckList> checkLists = new ArrayList<>();
}
