package pl.wojak.geoquiz.enums;


import lombok.Getter;

public enum DifficultyLevelEnum {

    PODSTAWOWY(1, "podstawowy"),
    ŚREDNIOZAAWANSOWANY(2, "średniozaawansowany"),
    ZAAWANSOWANY(3, "zaawansowany");

    @Getter
    private Integer kod;

    @Getter
    private String name;

    DifficultyLevelEnum(Integer kod, String name) {
        this.kod = kod;
        this.name = name;
    }
}
