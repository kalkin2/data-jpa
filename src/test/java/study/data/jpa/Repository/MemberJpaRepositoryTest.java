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

    @Test
    public void pagingTest(){
        //given
        memberJpaRepository.save(new Member("member1",7, null));
        memberJpaRepository.save(new Member("member2",10, null));
        memberJpaRepository.save(new Member("member3",24, null));
        memberJpaRepository.save(new Member("member4",24, null));
        memberJpaRepository.save(new Member("member5",24, null));
        memberJpaRepository.save(new Member("member6",24, null));
        memberJpaRepository.save(new Member("member7",24, null));
        memberJpaRepository.save(new Member("member8",24, null));
        memberJpaRepository.save(new Member("member9",24, null));
        memberJpaRepository.save(new Member("member10",24, null));
        memberJpaRepository.save(new Member("member11",24, null));
        memberJpaRepository.save(new Member("member12",24, null));
        memberJpaRepository.save(new Member("member13",24, null));
        memberJpaRepository.save(new Member("member14",24, null));
        memberJpaRepository.save(new Member("member15",24, null));
        memberJpaRepository.save(new Member("member16",24, null));


        int age = 24;
        int offset = 0;
        int limit = 10;//10개씩
        List<Member> byPage = memberJpaRepository.findByPage(age, offset,limit);
        long totalCnt = memberJpaRepository.totalCnt(age);

        assertThat(byPage.size()).isEqualTo(limit);

        assertThat(totalCnt).isEqualTo(14);


    }

}