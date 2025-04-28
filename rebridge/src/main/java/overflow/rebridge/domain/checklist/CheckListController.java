package overflow.rebridge.domain.checklist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CheckListController {

    @Autowired
    CheckListService checkListService;
}
