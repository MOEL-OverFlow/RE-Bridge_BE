package overflow.rebridge.domain.educationprogram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EducationProgramService {

    @Autowired
    EducationProgramRepository educationProgramRepository;
}
