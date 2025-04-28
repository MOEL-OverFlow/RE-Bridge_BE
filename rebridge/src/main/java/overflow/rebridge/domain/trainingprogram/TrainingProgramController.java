package overflow.rebridge.domain.trainingprogram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TrainingProgramController {

    @Autowired
    TrainingProgramService trainingProgramService;
}
