package pl.wojak.geoquiz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user", schema = "geo_schema")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Transient
    private static final String ANONYMOUS_PASSWORD = "123123";
    @Transient
    private static final String ANONYMOUS_EMAIL = "anonymous@anonymous.pl";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<GameEntity> games;

    private String password;

    @Transient // this annotation cause there won't be such column in database
    private String verifiedPassword;


    public UserEntity(String userName) {
        this.userName = userName;
        this.email = ANONYMOUS_EMAIL;
        this.password = ANONYMOUS_PASSWORD;
        GameEntity game = new GameEntity();
        games = new HashSet<>();
        games.add(game);
    }


}


//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "zad_zadania_seq")
//    @SequenceGenerator(name = "zad_zadania_seq", sequenceName = "zad_zadania_s", allocationSize = ALLOCATION_SIZE)
//    private Long id;
