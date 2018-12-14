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


//    todo poczytać z generated value:
// org.postgresql.util.PSQLException: BŁĄD: podwójna wartość klucza narusza ograniczenie unikalności "user_pkey"
//    Szczegóły: Klucz (id)=(1) już istnieje.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    private String email;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private Set<GameEntity> games;

    private String password;

    @Transient // this annotation cause there won't be such column in database
    private String verifiedPassword;


}


//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "zad_zadania_seq")
//    @SequenceGenerator(name = "zad_zadania_seq", sequenceName = "zad_zadania_s", allocationSize = ALLOCATION_SIZE)
//    private Long id;
