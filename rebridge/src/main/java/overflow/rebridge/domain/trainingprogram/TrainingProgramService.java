package overflow.rebridge.domain.trainingprogram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingProgramService {

    @Autowired
    TrainingProgramRepository trainingProgramRepository;
}
