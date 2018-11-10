package pl.wojak.geoquiz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "country", schema = "geo_schema")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String continent;

    @Column(name = "country_name")
    private String countryName;

    private String capital;
}
