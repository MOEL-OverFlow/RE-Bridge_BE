package overflow.rebridge.domain.checklist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CheckListRepository extends JpaRepository<CheckList, Long> {
    Optional<CheckList> findByMemberMemberId(Long memberId);
}
