package pl.wojak.geoquiz.converter;

import pl.wojak.geoquiz.enums.AreaEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter
public class AreaConverter implements AttributeConverter<AreaEnum, String> {
    @Override
    public String convertToDatabaseColumn(AreaEnum areaEnum) {
        if (areaEnum == null) {
            return null;
        }
        return areaEnum.getName();
    }

    @Override
    public AreaEnum convertToEntityAttribute(String attr) {
        return Stream.of(AreaEnum.values())
                .filter(s -> s.getName().equals(attr))
                .findFirst()
                .orElse(null);
    }
}
