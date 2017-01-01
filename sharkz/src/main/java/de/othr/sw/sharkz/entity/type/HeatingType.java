package de.othr.sw.sharkz.entity.type;

public enum HeatingType {
    SOLAR("Solar"), WIND("Wind"), WOOD("Holz"), PELLET("Pellet"),
    ELECTRICITY("Elektrisch"), OIL("Öl"), GAS("Gas");
    
    private final String label;
    
    private HeatingType(String label) {
        this.label = label;
    }
    
    public String getLabel() {
        return this.label;
    }
}
