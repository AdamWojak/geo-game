package pl.wojak.geoquiz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import pl.wojak.geoquiz.converter.AreaConverter;
import pl.wojak.geoquiz.converter.DifficultyLevelConverter;
import pl.wojak.geoquiz.enums.DifficultyLevelEnum;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "game", schema = "geo_schema")
@Data
@ToString
@AllArgsConstructor
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "user_game_id")
    private Long userGameId;

    @Column(name = "amount_of_points")
    private Integer amountOfPoints;

    @Column(name = "amount_of_attempts")
    private Integer amountOfAttempts;

    @Column(name = "modification_date")
    private LocalDateTime modificationDate;

    @Column(name = "level", columnDefinition = "integer")
    @Convert(converter = DifficultyLevelConverter.class)
    private DifficultyLevelEnum level;

    @Column(name = "area", columnDefinition = "varchar")
    @Convert(converter = AreaConverter.class)
    private String area;


    public GameEntity() {
        this.id = -1L;
        this.amountOfPoints = 0;
        this.amountOfAttempts = 0;
        this.modificationDate = LocalDateTime.now();
    }

    public GameEntity(UserEntity user) {
        this.user = user;
        this.amountOfPoints = 0;
        this.amountOfAttempts = 0;
        this.modificationDate = LocalDateTime.now();
    }
}
