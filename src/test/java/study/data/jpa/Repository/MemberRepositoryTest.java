package study.data.jpa.Repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import study.data.jpa.entity.Member;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testMember(){
        Member member = new Member("memberB");
        Member savedMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(savedMember.getId()).get();


        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());

    }

    @Test
    public void greaterThenTest(){
        //save
        Member member1 = new Member("a1",24, null);
        Member member2 = new Member("a2",25, null);
        Member member3 = new Member("a3",30, null);

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("a3", 29);

        assertThat(result.get(0).getAge()).isEqualTo(30);
        assertThat(result.size()).isEqualTo(1);


    }

}