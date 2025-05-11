package overflow.rebridge.domain.member;

import jakarta.persistence.*;
import lombok.Getter;
import overflow.rebridge.domain.nation.Nation;
import overflow.rebridge.domain.checklist.CheckList;
import overflow.rebridge.domain.image.Image;
import overflow.rebridge.domain.interest.Interest;
import overflow.rebridge.global.entity.BaseTimeEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "member")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "member_name")
    private String name;

    @Column(name = "member_email")
    private String email;

    @Column(name = "member_password")
    private String password;

    @Column(name = "birthDate")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;  // Role 필드 추가

    @Enumerated(EnumType.STRING)
    @Column(name = "login_type", nullable = false)
    private LoginType loginType;

    @ManyToOne
    @JoinColumn(name = "nation_id")
    private Nation nation;

    @OneToMany(mappedBy = "member")
    private List<Interest> interest = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<CheckList> checkLists = new ArrayList<>();

    public Member() {

    }

    public Member(String name, String email, Role role, LoginType loginType) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.loginType = loginType;
    }

}
