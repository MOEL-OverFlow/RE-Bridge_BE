package overflow.rebridge.domain.insurance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class InsuranceController {

    @Autowired
    InsuranceService insuranceService;

}
