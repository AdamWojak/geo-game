package pl.wojak.geoquiz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static pl.wojak.geoquiz.constant.ANONYMOUS_EMAIL;
import static pl.wojak.geoquiz.constant.ANONYMOUS_PASSWORD;

@Entity
@Table(name = "user", schema = "geo_schema")
@Data
@ToString(exclude = "games")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    private String email;

    private String password;

    @Transient
    private String verifiedPassword;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<GameEntity> games = new ArrayList<>();


    public UserEntity(String userName) {
        this.userName = userName;
        this.email = ANONYMOUS_EMAIL;
        this.password = ANONYMOUS_PASSWORD;
    }

}

