package study.data.jpa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of ={"id","username","age"}) // 주의: 연관 객체를 찍지 않도록 한다.
//Setter는 권장되지 않음.
public class Member {

    //GeneratedValue => autoIncrement 같은기능
    //ID =>Pk
    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;
    private String username;
    private int age;

    //FK
    //실무에서는 거의 LAZY 로 세팅하여 사용함. 지연로딩 : 필요할때만 연관관계 테이블을 조회함.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="team_id")
    private Team team;

    public Member(String username){
        this.username = username;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if(team!=null) {
            changeTeam(team);
        }
    }

    public void  changeTeam(Team team){
        this.team = team;
        // 역방향 연관관계를 설정하지 않아도, 지연 로딩을 통해서 아래에서 Member에 접근할 수 있다.
//        team.getMembers().add(this);
    }

}
