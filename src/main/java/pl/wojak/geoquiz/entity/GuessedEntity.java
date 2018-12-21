package pl.wojak.geoquiz.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "guessed", schema = "geo_schema")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GuessedEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private GameEntity game;

    @OneToOne()
    @JoinColumn(name = "country_id")
    private CountryEntity country;

    public GuessedEntity(GameEntity game, CountryEntity country) {
        this.game = game;
        this.country = country;
    }
}
