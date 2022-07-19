package study.data.jpa.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
//Setter는 권장되지 않음.
public class Member {

    //GeneratedValue => autoIncrement 같은기능
    //ID =>Pk
    @Id @GeneratedValue
    private Long id;
    private String username;

    protected Member(){}

    public Member(String username){
        this.username = username;
    }

}
