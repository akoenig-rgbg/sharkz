package de.othr.sw.sharkz.entity;

import de.othr.sw.sharkz.entity.type.HeatingType;
import javax.persistence.Entity;

@Entity
public class LivingInsertion extends Insertion {
    private int livingArea;
    private int plotArea;
    private int rooms;
    private int stages;
    private HeatingType heating;
    private boolean guestToilette;
    private boolean basement;
    private boolean kitchen;
    private boolean newBuild;
    private boolean garage;
    private boolean steplessEntry;
    private boolean lift;
    
    public LivingInsertion() {
        super();
    }

    public int getLivingArea() {
        return livingArea;
    }

    public void setLivingArea(int livingArea) {
        this.livingArea = livingArea;
    }

    public int getPlotArea() {
        return plotArea;
    }

    public void setPlotArea(int plotArea) {
        this.plotArea = plotArea;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public int getStages() {
        return stages;
    }

    public void setStages(int stages) {
        this.stages = stages;
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

    public boolean isLift() {
        return lift;
    }

    public void setLift(boolean lift) {
        this.lift = lift;
    }
    
    
}
