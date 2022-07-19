package study.data.jpa.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.data.jpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
