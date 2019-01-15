package pl.wojak.geoquiz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.wojak.geoquiz.converter.DifficultyLevelConverter;
import pl.wojak.geoquiz.enums.DifficultyLevelEnum;

import javax.persistence.*;

@Entity
@Table(name = "country", schema = "geo_schema")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CountryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String continent;

    @Column(name = "country_name")
    private String countryName;

    private String capital;

    @Column(name = "level", columnDefinition = "integer")
    @Convert(converter = DifficultyLevelConverter.class)
    private DifficultyLevelEnum level;
}
