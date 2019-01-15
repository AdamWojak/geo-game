package pl.wojak.geoquiz.converter;

import pl.wojak.geoquiz.enums.DifficultyLevelEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter
public class DifficultyLevelConverter implements AttributeConverter<DifficultyLevelEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(DifficultyLevelEnum difficultyLevelEnum) {
        if (difficultyLevelEnum == null) {
            return null;
        }
        return difficultyLevelEnum.getKod();
    }

    @Override
    public DifficultyLevelEnum convertToEntityAttribute(Integer attr) {
        return Stream.of(DifficultyLevelEnum.values())
                .filter(i -> i.getKod().equals(attr))
                .findFirst()
                .orElse(null);
    }
}
