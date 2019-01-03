package pl.wojak.geoquiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CountryFormDTO {

    private List<CountryDTO> countriesFormDTO;


    public CountryFormDTO() {
        this.countriesFormDTO = new ArrayList<>();
    }

}
