package pl.wojak.geoquiz.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CountryDTO {

    private Long id;

    private String continent;

    private String countryName;

    private String capital;

    private String guessedCapital;

    private Boolean result;






}
