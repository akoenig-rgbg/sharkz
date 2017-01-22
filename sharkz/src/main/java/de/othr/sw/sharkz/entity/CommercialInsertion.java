package de.othr.sw.sharkz.entity;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.persistence.Entity;

@Entity
public class CommercialInsertion extends Insertion {
    private int area;
    private boolean aircon;
    private boolean heavyCurrent;

    @Override
    public Map<String, String> getImportantAttributes() {
        Map<String, String> attrs = new LinkedHashMap<>();
        
        attrs.put("Fläche", String.valueOf(this.getArea()));
        
        return attrs;
    }
    
    @Override
    public Map<String, String> getFurtherAttributes() {
        Map<String, String> attrs = getAttributes();
        
        // Strings
        attrs.put("Fläche", String.valueOf(this.area));
        
        // Booleans
        String s;
        String tr = "<i class=\"fa fa-check\" aria-hidden=\"true\"/>";
        String fa = "<i class=\"fa fa-times\" aria-hidden=\"true\"/>";
        
        attrs.put("Klimaanlage", s = this.aircon ? tr : fa);
        attrs.put("Starkstromanschluss", s = this.heavyCurrent ? tr : fa);
                
        return attrs;
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
