package pl.wojak.geoquiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class GameParamFormDTO {

    private List<String> continents;

    private List<String> levels;


    public GameParamFormDTO() {
        this.continents = new ArrayList<>();
        this.levels = createLevels();
    }

    public List<String> createLevels() {
        List<String> difficultyLevel = new ArrayList<>();
        difficultyLevel.add("podstawowy");
        difficultyLevel.add("zaawansowany");
        return difficultyLevel;
    }

}
