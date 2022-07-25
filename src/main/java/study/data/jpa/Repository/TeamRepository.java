package study.data.jpa.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.data.jpa.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
