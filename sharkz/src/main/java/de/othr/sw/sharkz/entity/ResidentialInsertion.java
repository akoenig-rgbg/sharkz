package de.othr.sw.sharkz.entity;

import de.othr.sw.sharkz.entity.type.HeatingType;
import de.othr.sw.sharkz.entity.type.OfferType;
import java.io.File;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class ResidentialInsertion extends BasicInsertion {
    private int livingArea;
    private int rooms;
    @Enumerated(EnumType.STRING)
    private HeatingType heating;
    private boolean guestToilette;
    private boolean basement;
    private boolean kitchen;
    private boolean newBuild;
    private boolean garage;
    private boolean steplessEntry;

    public ResidentialInsertion() {
        super();
    }
    
    public ResidentialInsertion(long pricePerMonth, OfferType offerType,
            String description, Address address, long price,
            List<File> images, Customer vendor, int livingArea, int rooms,
            HeatingType heating, boolean guestToilette, boolean basement,
            boolean kitchen, boolean newBuild, boolean garage,
            boolean steplessEntry) {
        super(pricePerMonth, offerType, description, address, price, images,
                vendor);
        
        this.livingArea = livingArea;
        this.rooms = rooms;
        this.heating = heating;
        this.guestToilette = guestToilette;
        this.basement = basement;
        this.kitchen = kitchen;
        this.newBuild = newBuild;
        this.garage = garage;
        this.steplessEntry = steplessEntry;
    }
    
    public int getLivingArea() {
        return livingArea;
    }

    public void setLivingArea(int livingArea) {
        this.livingArea = livingArea;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public HeatingType getHeating() {
        return heating;
    }

    public void setHeating(HeatingType heating) {
        this.heating = heating;
    }

    public boolean isGuestToilette() {
        return guestToilette;
    }

    public void setGuestToilette(boolean guestToilette) {
        this.guestToilette = guestToilette;
    }

    public boolean isBasement() {
        return basement;
    }

    public void setBasement(boolean basement) {
        this.basement = basement;
    }

    public boolean isKitchen() {
        return kitchen;
    }

    public void setKitchen(boolean kitchen) {
        this.kitchen = kitchen;
    }

    public boolean isNewBuild() {
        return newBuild;
    }

    public void setNewBuild(boolean newBuild) {
        this.newBuild = newBuild;
    }

    public boolean isGarage() {
        return garage;
    }

    public void setGarage(boolean garage) {
        this.garage = garage;
    }

    public boolean isSteplessEntry() {
        return steplessEntry;
    }

    public void setSteplessEntry(boolean steplessEntry) {
        this.steplessEntry = steplessEntry;
    }
}
