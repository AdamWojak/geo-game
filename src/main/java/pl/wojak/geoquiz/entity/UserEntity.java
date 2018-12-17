package pl.wojak.geoquiz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    private String password;

    @Transient // this annotation cause there won't be such column in database
    private String verifiedPassword;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<GameEntity> games;

    public UserEntity(String userName) {
        this.userName = userName;
        this.email = ANONYMOUS_EMAIL;
        this.password = ANONYMOUS_PASSWORD;
        GameEntity game = new GameEntity();
        games = new ArrayList<>();
        games.add(game);
    }


}


//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "zad_zadania_seq")
//    @SequenceGenerator(name = "zad_zadania_seq", sequenceName = "zad_zadania_s", allocationSize = ALLOCATION_SIZE)
//    private Long id;
