package de.othr.sw.sharkz.model;

import de.othr.sw.sharkz.entity.type.HouseType;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@ViewScoped
@Named("test")
public class TestModel implements Serializable {
    
    private HouseType houseType;
    
    public String action() {
        return null;
    }

    public HouseType getHouseType() {
        System.out.println("GET: " + houseType);
        return houseType;
    }

    public void setHouseType(HouseType houseType) {
        System.out.println("SET: " + this.houseType + " = " + houseType);
        this.houseType = houseType;
    }
    
    
}
