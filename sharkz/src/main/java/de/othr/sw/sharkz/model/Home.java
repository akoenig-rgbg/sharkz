package de.othr.sw.sharkz.model;

import de.othr.sw.sharkz.entity.type.OfferType;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named(value="home")
@RequestScoped
public class Home {
    
    private Map<String, String> searchMap = new HashMap<>();

    public Map<String, String> getSearchMap() {
        return searchMap;
    }

    public void setSearchMap(Map<String, String> searchMap) {
        this.searchMap = searchMap;
    }
}
