package de.othr.sw.sharkz.entity;

import javax.persistence.Entity;

import de.othr.sw.sharkz.entity.type.CommercialType;
import de.othr.sw.sharkz.entity.type.OfferType;
import java.io.File;
import java.util.List;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class CommercialInsertion extends BasicInsertion {
    @Enumerated(EnumType.STRING)
    private CommercialType type;
    private int area;
    private boolean aircon;
    private boolean heavyCurrent;
    
    public CommercialInsertion(long pricePerMonth, OfferType offerType,
            String description, Address address, long price, List<File> images,
            Customer vendor, CommercialType type, int area, boolean aircon,
            boolean heavyCurrent) {
        super(pricePerMonth, offerType, description, address, price, images,
                vendor);
        
        this.type = type;
        this.area = area;
        this.aircon = aircon;
        this.heavyCurrent = heavyCurrent;
    }

    public CommercialType getType() {
        return type;
    }

    public void setType(CommercialType type) {
        this.type = type;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public boolean isAircon() {
        return aircon;
    }

    public void setAircon(boolean aircon) {
        this.aircon = aircon;
    }

    public boolean isHeavyCurrent() {
        return heavyCurrent;
    }

    public void setHeavyCurrent(boolean heavyCurrent) {
        this.heavyCurrent = heavyCurrent;
    }
}
