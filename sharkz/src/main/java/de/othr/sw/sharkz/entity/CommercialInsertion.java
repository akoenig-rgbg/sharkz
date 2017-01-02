package de.othr.sw.sharkz.entity;

import javax.persistence.Entity;

@Entity
public class CommercialInsertion extends Insertion {
    private int area;
    private boolean aircon;
    private boolean heavyCurrent;

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
