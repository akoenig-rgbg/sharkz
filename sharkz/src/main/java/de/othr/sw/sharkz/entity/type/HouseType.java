package de.othr.sw.sharkz.entity.type;

public enum HouseType {
    VILLA("Villa"), SINGLE_FAMILY("Einfamilienhaus"), 
    EMI_DETACHED("Doppelhaushälfte"), ROW_HOUSE("Reihenhaus"),
    APARTMENT("Wohnung"), GROUND_FLOOR("Erdgeschosswohnung"),
    ATTIC("Dachgeschosswohnung"), PENTHOUSE("Penthouse"), BUREAU("Büro"),
    WAREHOUSE("Lagerhalle"), SURGERY("Praxis");
    
    private final String label;
    
    public boolean isLiving() {
        if (this == HouseType.BUREAU
                || this == HouseType.WAREHOUSE 
                || this == HouseType.SURGERY) {
            return false;
        }
        
        return true;
    }
    
   
    
    private HouseType(String label) {
        this.label = label;
    }
    
    public String getLabel() {
        return this.label;
    }
}