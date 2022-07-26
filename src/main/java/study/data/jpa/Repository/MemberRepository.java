package study.data.jpa.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.data.jpa.dto.MemberDto;
import study.data.jpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member>findUser(@Param("username")String username, @Param("age")int age);

    @Query("select new study.data.jpa.dto.MemberDto( m.id, m.username, t.name ) from Member m  join m.team t")
    List<MemberDto> findMemberDto();


    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);


    Page<Member> findByAge(int age, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age  = m.age + 1 where m.age >=:age")
    int bulkAgeUpdate(@Param("age") int age);

    Member findByUsername(String member5);

    @Query("select m from Member m left join fetch m.team")
    List<Member> findTeamFetchJoin();

    @Override
    @EntityGraph(attributePaths = "team")
    List<Member> findAll();
}
