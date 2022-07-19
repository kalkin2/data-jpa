package study.data.jpa.Repository;

import org.springframework.stereotype.Repository;
import study.data.jpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberJpaRepository {

    @PersistenceContext
    private EntityManager em;

    /**
     * 등록
     * @param member
     * @return
     */
    public Member save(Member member){
        em.persist(member);
        return member;
    }


    /**
     * 조회
     * @param id
     * @return
     */
    public Member find(Long id){
        return em.find(Member.class,id);
    }
}
