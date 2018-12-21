package pl.wojak.geoquiz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "game", schema = "geo_schema")
@Data
@ToString
@AllArgsConstructor
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount_of_points")
    private Integer amountOfPoints;

    @Column(name = "amount_of_attempts")
    private Integer amountOfAttempts;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private UserEntity user;


    public GameEntity() {
        this.amountOfPoints = 0;
        this.amountOfAttempts = 0;
    }

    public GameEntity(UserEntity user) {
        this.amountOfPoints = 0;
        this.amountOfAttempts = 0;
        this.user = user;
    }
}
