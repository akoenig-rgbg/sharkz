package de.othr.sw.sharkz.entity;

import de.othr.sw.sharkz.entity.type.HouseType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class HouseInsertion extends ResidentialInsertion {
    @Enumerated(EnumType.STRING)
    private HouseType type;
    private int plotArea;

    public HouseType getType() {
        return type;
    }

    public void setType(HouseType type) {
        this.type = type;
    }

    public int getPlotArea() {
        return plotArea;
    }

    public void setPlotArea(int plotArea) {
        this.plotArea = plotArea;
    }
}
