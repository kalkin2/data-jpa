package study.data.jpa.entity;



import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {

    @Id
    @GeneratedValue
    private Long id;
    private String name;


    public Team(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "team")
    private List<Member>members = new ArrayList<>();

}
