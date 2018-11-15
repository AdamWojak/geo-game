package pl.wojak.geoquiz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user", schema = "geo_schema")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<GameEntity> games;

    private String password;

    @Transient // this annotation cause there won't be such column in database
    private String verifiedPassword;


}
