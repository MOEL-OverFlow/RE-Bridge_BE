package overflow.rebridge.domain.member;

import jakarta.persistence.*;
import lombok.Getter;
import overflow.rebridge.domain.auth.dto.FindPasswordRequest;
import overflow.rebridge.domain.auth.dto.SignupRequest;
import overflow.rebridge.domain.bookmark.Bookmark;
import overflow.rebridge.domain.checklist.CheckList;
import overflow.rebridge.domain.image.Image;
import overflow.rebridge.domain.jobPosting.Field;
import overflow.rebridge.domain.member.dto.LoginType;
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

    @Column(name = "member_email", unique = true)
    private String email;

    @Column(name = "member_password")
    private String password;

    @Column(name = "birthDate")
    private LocalDate birthDate;

    @Column(name = "foreigner_number", unique = true)
    private String foreignerNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;  // Role 필드 추가

    @Enumerated(EnumType.STRING)
    @Column(name = "login_type", nullable = false)
    private LoginType loginType;

    @Enumerated(EnumType.STRING)
    private Nation nation;

    @Enumerated(EnumType.STRING)
    private Field field1;

    @Enumerated(EnumType.STRING)
    private Field field2;

    @OneToOne(mappedBy = "member")
    private Image image;

    @OneToMany(mappedBy = "member")
    private List<CheckList> checkLists = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks = new ArrayList<>();

    public Member() {

    }

    public Member(String name, String email, Role role, LoginType loginType, String foreignerNumber) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.loginType = loginType;
        this.foreignerNumber = foreignerNumber;
    }

    public Member(SignupRequest request, String encodedPassword) {
        this.email = request.email();
        this.password = encodedPassword;
        this.name = request.name();
        this.birthDate = request.birthDate();
        this.foreignerNumber = request.foreignerNumber();
        this.nation = Nation.valueOf(request.nation());
        this.field1 = Field.valueOf(request.industry1());
        this.field2 = Field.valueOf(request.industry2());
        this.loginType = LoginType.valueOf(request.loginType());
        this.role = Role.MEMBER;
    }

    public void updateInfo(SignupRequest request, String encodedPassword) {
        this.password = encodedPassword;
        this.name = request.name();
        this.birthDate = request.birthDate();
        this.foreignerNumber = request.foreignerNumber();
        this.nation = Nation.valueOf(request.nation());
        this.field1 = Field.valueOf(request.industry1());
        this.field2 = Field.valueOf(request.industry2());
        this.loginType = LoginType.valueOf(request.loginType());
        this.role = Role.MEMBER;
    }

    public boolean check(FindPasswordRequest request) {
        if(!this.email.equals(request.email())) {
            return false;
        }
        else if (!this.name.equals(request.name())) {
            return false;
        }
        else if (!this.foreignerNumber.equals(request.foreignerNumber())) {
            return false;
        }
        else if (!this.birthDate.equals(request.birthDate())) {
            return false;
        }
        return true;
    }

    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
