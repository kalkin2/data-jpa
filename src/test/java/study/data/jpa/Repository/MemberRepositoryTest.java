package study.data.jpa.Repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import study.data.jpa.dto.MemberDto;
import study.data.jpa.entity.Member;
import study.data.jpa.entity.Team;

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

    @Autowired
    TeamRepository teamRepository;

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

    @Test
    public void findUserTest(){
        Member member1 = new Member("a1",24, null);
        Member member2 = new Member("a2",25, null);
        Member member3 = new Member("a3",30, null);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        List<Member> result = memberRepository.findUser("a1", 24);
        assertThat(result.get(0).getUsername()).isEqualTo("a1");
        assertThat(result.get(0).getAge()).isEqualTo(24);

    }

    @Test
    public void findMemberDto(){

        Team team1 = new Team("a1team");
        Member member1 = new Member("a1",24, team1);

        teamRepository.save(team1);
        memberRepository.save(member1);


        List<MemberDto> memberDto = memberRepository.findMemberDto();
        memberDto.forEach(System.out::println);
        assertThat(memberDto.size()).isEqualTo(1);
        assertThat(memberDto.get(0).getTeamName()).isEqualTo("a1team");
    }

    @Test
    public void findByNames(){
        Member member1 = new Member("a1",24, null);
        Member member2 = new Member("a2",25, null);
        Member member3 = new Member("a3",30, null);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        List<Member> result = memberRepository.findByNames(List.of("a1","a2"));
        result.forEach(System.out::println);

        assertThat(result.size()).isEqualTo(2);

    }


    @Test
    public void pagingTest(){
        //given
        memberRepository.save(new Member("member1",7, null));
        memberRepository.save(new Member("member2",10, null));
        memberRepository.save(new Member("member3",24, null));
        memberRepository.save(new Member("member4",24, null));
        memberRepository.save(new Member("member5",24, null));
        memberRepository.save(new Member("member6",24, null));
        memberRepository.save(new Member("member7",24, null));
        memberRepository.save(new Member("member8",24, null));
        memberRepository.save(new Member("member9",24, null));
        memberRepository.save(new Member("member10",24, null));
        memberRepository.save(new Member("member11",24, null));
        memberRepository.save(new Member("member12",24, null));
        memberRepository.save(new Member("member13",24, null));
        memberRepository.save(new Member("member14",24, null));
        memberRepository.save(new Member("member15",24, null));
        memberRepository.save(new Member("member16",24, null));


        int age = 24;
        int offset = 0;
        int limit = 10;//10개씩

        PageRequest pageRequest = PageRequest.of(offset, limit, Sort.by(Sort.Direction.DESC, "username"));

        //when
        Page<Member> page = memberRepository.findByAge(age, pageRequest);

        //entity->dto
        Page<MemberDto>pageDtos = page.map(entity->new MemberDto(entity.getId()+100,entity.getUsername(),null)
        );

        //then
        List<MemberDto> content = pageDtos.getContent();
        long totalElements = pageDtos.getTotalElements();
        int totalPages = pageDtos.getTotalPages();
        int size = pageDtos.getSize();

        content.forEach(System.out::println);

        assertThat(totalElements).isEqualTo(14);
        assertThat(totalPages).isEqualTo(2);
        assertThat(size).isEqualTo(10);
        assertThat(page.isFirst()).isTrue();



    }

    @Test
    public void bulkAgeUpdate(){

       memberRepository.save(new Member("member1",10, null));
       memberRepository.save(new Member("member2",20, null));
       memberRepository.save(new Member("member3",30, null));
       memberRepository.save(new Member("member4",40, null));
       memberRepository.save(new Member("member5",50, null));

        int updateCount = memberRepository.bulkAgeUpdate(30);

        assertThat(updateCount).isEqualTo(3);
        Member member5 = memberRepository.findByUsername("member5");
        System.out.println("member5::"+member5);
        assertThat(member5.getAge()).isEqualTo(51);
    }

}