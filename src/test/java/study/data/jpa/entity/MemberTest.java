package study.data.jpa.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import study.data.jpa.Repository.MemberRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberTest {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testEntity(){
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        Team teamC = new Team("teamC");

        entityManager.persist(teamA);
        entityManager.persist(teamB);
        entityManager.persist(teamC);

        Member member1 = new Member("member1",10,teamA);
        Member member2 = new Member("member2",20,teamA);
        Member member3 = new Member("member3",30,teamB);
        Member member4 = new Member("member4",40,teamB);

        entityManager.persist(member1);
        entityManager.persist(member2);
        entityManager.persist(member3);
        entityManager.persist(member4);



        List<Member> resultList = entityManager.createQuery("select m from Member m", Member.class).getResultList();

        System.out.println("RESULT2222::");
        // 연관관계의 주인에 값 설정
        member4.changeTeam(teamC);
        entityManager.persist(member4);

        //commit 작업.
        entityManager.flush();
        entityManager.clear();


//        Team findTeam = entityManager.find(Team.class, teamB.getId());
//        List<Member> findMembers = findTeam.getMembers();
//        System.out.println("teamB size is ::: "+findMembers.size());

        entityManager.find(Member.class, member4.getId());

    }

}