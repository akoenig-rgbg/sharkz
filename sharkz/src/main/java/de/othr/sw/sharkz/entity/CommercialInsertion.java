package de.othr.sw.sharkz.entity;

import javax.persistence.Entity;

import de.othr.sw.sharkz.entity.type.CommercialType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class CommercialInsertion extends BasicInsertion {
    @Enumerated(EnumType.STRING)
    private CommercialType type;
    private int area;
    private boolean aircon;
    private boolean heavyCurrent;

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
