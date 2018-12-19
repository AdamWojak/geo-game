package pl.wojak.geoquiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.wojak.geoquiz.entity.CountryEntity;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CountryCreateDTO {

    private List<CountryEntity> countries;
    private List<String> guessedCapitals;
    private List<Boolean> result;

    public CountryCreateDTO() {
        this.countries = new ArrayList<>();
    }

    public void addCountry(CountryEntity country) {
        this.countries.add(country);
    }
}
