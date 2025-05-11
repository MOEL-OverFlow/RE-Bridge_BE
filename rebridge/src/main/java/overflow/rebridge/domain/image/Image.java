package overflow.rebridge.domain.image;

import jakarta.persistence.*;
import overflow.rebridge.domain.member.Member;

@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "image_url")
    private String url;

    public Image(String imageUrl, Member member) {
        this.url = imageUrl;
        this.member = member;
    }

    public Image() {

    }
}
