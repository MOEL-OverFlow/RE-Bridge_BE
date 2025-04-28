package overflow.rebridge.domain.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

    @Autowired
    DocumentRepository documentRepository;
}
