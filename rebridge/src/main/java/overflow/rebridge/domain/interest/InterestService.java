package overflow.rebridge.domain.interest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterestService {

    @Autowired
    InterestRepository interestRepository;
}
