package overflow.rebridge.domain.industrycategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndustryCategoryService {

    @Autowired
    IndustryCategoryRepository industryCategoryRepository;
}
