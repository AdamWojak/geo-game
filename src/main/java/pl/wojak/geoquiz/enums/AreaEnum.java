package pl.wojak.geoquiz.enums;


import lombok.Getter;

public enum AreaEnum {

    AZJA(1, "Azja"),
    EUROPA(2, "Europa"),
    CALY_SWIAT(3, "Cały świat");

    @Getter
    private Integer kod;

    @Getter
    private String name;

    AreaEnum(Integer kod, String name) {
        this.kod = kod;
        this.name = name;
    }
}
