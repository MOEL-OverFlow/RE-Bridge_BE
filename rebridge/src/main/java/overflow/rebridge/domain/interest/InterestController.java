package overflow.rebridge.domain.interest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class InterestController {

    @Autowired
    InterestService interestService;
}
