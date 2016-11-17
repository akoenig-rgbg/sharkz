package de.othr.sw.sharkz.entity;

import de.othr.sw.sharkz.entity.type.HeatingType;
import de.othr.sw.sharkz.entity.type.HouseType;
import de.othr.sw.sharkz.entity.type.OfferType;
import java.io.File;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class HouseInsertion extends ResidentialInsertion {
    @Enumerated(EnumType.STRING)
    private HouseType type;
    private int plotArea;

    public HouseInsertion(HouseType type, int plotArea, long pricePerMonth,
            OfferType offerType, String description, Address address,
            long price, List<File> images, Customer vendor, int livingArea,
            int rooms, HeatingType heating, boolean guestToilette,
            boolean basement, boolean kitchen, boolean newBuild, boolean garage,
            boolean steplessEntry) {
        super(pricePerMonth, offerType, description, address, price, images,
                vendor, livingArea, rooms, heating, guestToilette, basement,
                kitchen, newBuild, garage, steplessEntry);
      
        this.type = type;
        this.plotArea = plotArea;
    }

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
