package study.data.jpa.Repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.data.jpa.entity.Member;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)//LocalTest 를 위한용도
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember(){
        Member member = new Member("juguemseok");

        Member savedMember = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.find(savedMember.getId());

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);

    }

    @Test
    public void greaterThenTest(){
        //save
        Member member1 = new Member("a1",24, null);
        Member member2 = new Member("a2",25, null);
        Member member3 = new Member("a3",30, null);

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        memberJpaRepository.save(member3);

        List<Member> result = memberJpaRepository.findByUsernameAngAgeGreaterThen("a3", 29);

        assertThat(result.get(0).getAge()).isEqualTo(30);
        assertThat(result.size()).isEqualTo(1);


    }

}