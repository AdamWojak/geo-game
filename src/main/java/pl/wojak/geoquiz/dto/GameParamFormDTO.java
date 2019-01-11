package pl.wojak.geoquiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wojak.geoquiz.enums.AreaEnum;
import pl.wojak.geoquiz.enums.DifficultyLevelEnum;

import java.util.Arrays;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class GameParamFormDTO {

    private List<AreaEnum> continents;

    private List<DifficultyLevelEnum> levels;

    public GameParamFormDTO() {
        this.continents = Arrays.asList(AreaEnum.values());
        this.levels = Arrays.asList(DifficultyLevelEnum.values());
    }
}
