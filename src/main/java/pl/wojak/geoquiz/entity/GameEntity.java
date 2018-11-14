package pl.wojak.geoquiz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "game", schema = "geo_schema")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "amount_of_points")
    private Integer amountOfPoints;

    @Column(name = "amount_of_attempts")
    private Integer amountOfAttempts;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
