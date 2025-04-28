package overflow.rebridge.domain.checklist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckListService {

    @Autowired
    CheckListRepository checkListRepository;
}
