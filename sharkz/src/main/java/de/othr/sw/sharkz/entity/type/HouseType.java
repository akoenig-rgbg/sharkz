package de.othr.sw.sharkz.entity.type;

import java.util.ArrayList;
import java.util.List;

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
    
    public static List<HouseType> getTypesOf(UsageType usage) {
        List<HouseType> types = new ArrayList<>();
        
        if (usage == UsageType.COMMERCIAL) {
            types.add(HouseType.SURGERY);
            types.add(HouseType.BUREAU);
            types.add(HouseType.WAREHOUSE);
        } else {
            types.add(HouseType.APARTMENT);
            types.add(HouseType.ATTIC);
            types.add(HouseType.EMI_DETACHED);
            types.add(HouseType.GROUND_FLOOR);
            types.add(HouseType.PENTHOUSE);
            types.add(HouseType.ROW_HOUSE);
            types.add(HouseType.SINGLE_FAMILY);
            types.add(HouseType.VILLA);
        }
        
        return types;
    }
    
    private HouseType(String label) {
        this.label = label;
    }
    
    public String getLabel() {
        return this.label;
    }
}