package de.othr.sw.sharkz.entity.type;

public enum UsageType {
    LIVING("Wohnen"), COMMERCIAL("Gewerbe");
    
    private final String label;
    
    private UsageType(String label) {
        this.label = label;
    }
    
    public String getLabel() {
        return label;
    }
}
