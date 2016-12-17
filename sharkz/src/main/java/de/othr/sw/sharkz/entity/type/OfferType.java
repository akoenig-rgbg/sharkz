package de.othr.sw.sharkz.entity.type;

public enum OfferType {
    PURCHASE("Kaufen"), RENT("Mieten");
    
    private final String label;
    
    private OfferType(String label) {
        this.label = label;
    }
    
    public String getLabel() {
        return this.label;
    }
}