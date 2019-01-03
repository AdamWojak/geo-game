package pl.wojak.geoquiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wojak.geoquiz.entity.GameEntity;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameFormDTO {

    private List<GameEntity> gamesFormDTO;
}
