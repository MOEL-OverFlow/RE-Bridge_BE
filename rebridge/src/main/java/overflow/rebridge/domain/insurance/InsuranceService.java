package overflow.rebridge.domain.insurance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InsuranceService {

    @Autowired
    InsuranceRepository insuranceRepository;
}
