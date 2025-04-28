package overflow.rebridge.domain.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ImageController {

    @Autowired
    ImageService imageService;
}
