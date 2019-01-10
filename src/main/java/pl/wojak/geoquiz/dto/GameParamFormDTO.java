package pl.wojak.geoquiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wojak.geoquiz.enums.DifficultyLevelEnum;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameParamFormDTO {

    private List<String> continents;

    private List<DifficultyLevelEnum> levels;

}
