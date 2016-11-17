package de.othr.sw.sharkz.entity;

import de.othr.sw.sharkz.entity.type.HeatingType;
import de.othr.sw.sharkz.entity.type.OfferType;
import java.io.File;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class ApartmentInsertion extends ResidentialInsertion {
    @Enumerated(EnumType.STRING)
    private ApartmentType type;
    private boolean lift;

    public ApartmentInsertion(ApartmentType type, boolean lift,
            long pricePerMonth, OfferType offerType, String description,
            Address address, long price, List<File> images, Customer vendor,
            int livingArea, int rooms, HeatingType heating,
            boolean guestToilette, boolean basement, boolean kitchen,
            boolean newBuild, boolean garage, boolean steplessEntry) {
        super(pricePerMonth, offerType, description, address, price, images,
                vendor, livingArea, rooms, heating, guestToilette, basement,
                kitchen, newBuild, garage, steplessEntry);
        
        this.type = type;
        this.lift = lift;
    }

    public ApartmentType getType() {
        return type;
    }

    public void setType(ApartmentType type) {
        this.type = type;
    }

    public boolean isLift() {
        return lift;
    }

    public void setLift(boolean lift) {
        this.lift = lift;
    }
}
