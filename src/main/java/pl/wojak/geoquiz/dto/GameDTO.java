package pl.wojak.geoquiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wojak.geoquiz.entity.UserEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameDTO {

    private Long id;

    private Integer amountOfPoints;

    private Integer amountOfAttempts;

    private UserEntity user;


}
