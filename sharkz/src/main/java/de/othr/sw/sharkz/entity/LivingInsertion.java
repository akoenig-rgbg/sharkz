package de.othr.sw.sharkz.entity;

import de.othr.sw.sharkz.entity.type.HeatingType;
import de.othr.sw.sharkz.entity.type.OfferType;
import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
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

    @Override
    public Map<String, String> getImportantAttributes() {
        
        Map<String, String> attrs = new LinkedHashMap<>();

        String priceLabel;
        if (this.getOfferType() == OfferType.PURCHASE)
            priceLabel = "Kaufpreis";
        else
            priceLabel = "Kaltmiete";
            
        String price = NumberFormat.getNumberInstance(
                Locale.GERMAN).format(this.getPrice());

        attrs.put(priceLabel, price + " &euro;");
        attrs.put("Zi.", String.valueOf(this.getRooms()));
        attrs.put("Wohnfläche", String.valueOf(this.getLivingArea()) + " m&sup2;");
        attrs.put("Grundfläche", String.valueOf(this.getPlotArea()) + " m&sup2;");
        
        return attrs;
    }
    
    @Override
    public Map<String, String> getFurtherAttributes() {
                
        Map<String, String> attrs = new LinkedHashMap<>();
        
        // Strings
        attrs.put("Wohnfläche", String.valueOf(this.livingArea) + " m&sup2;");
        attrs.put("Grundfläche", String.valueOf(this.plotArea) + " m&sup2;");
        attrs.put("Zimmer", String.valueOf(this.rooms));
        attrs.put("Etagen", String.valueOf(this.stages));
        attrs.put("Heizung", this.heating.getLabel());
        
        // Booleans
        String s;
        String tr = "<i class=\"fa fa-check\" aria-hidden=\"true\"/>";
        String fa = "<i class=\"fa fa-times\" aria-hidden=\"true\"/>";
        
        attrs.put("Gästetoilette", s = this.guestToilette ? tr : fa);
        attrs.put("Keller", s = this.basement ? tr : fa);
        attrs.put("Küche", s = this.kitchen ? tr : fa);
        attrs.put("Aufzug", s = this.lift ? tr : fa);
        attrs.put("Neubau", s = this.newBuild ? tr : fa);
        attrs.put("Garage", s = this.garage ? tr : fa);
        attrs.put("Stufenloser Zugang", s = this.steplessEntry ? tr : fa);
                
        return attrs;
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
