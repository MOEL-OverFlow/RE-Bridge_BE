package overflow.rebridge.domain.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import overflow.rebridge.domain.member.Member;

@Service
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

    public Image saveImage(String imageUrl, Member member) {
        Image image = new Image(imageUrl, member);
        return imageRepository.save(image);
    }
}
