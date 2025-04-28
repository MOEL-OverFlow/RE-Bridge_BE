package overflow.rebridge.domain.industrycategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndustryCategoryController {

    @Autowired
    IndustryCategoryService industryCategoryService;
}
