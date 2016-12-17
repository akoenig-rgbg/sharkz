package de.othr.sw.sharkz.model;

import de.othr.sw.sharkz.entity.BasicInsertion;
import de.othr.sw.sharkz.entity.type.OfferType;
import de.othr.sw.sharkz.entity.type.UsageType;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

@ManagedBean(name="search")
@Named(value="search")
@RequestScoped
public class Search {
    
    private UsageType usage;
    private String location;
    private OfferType offer;
    
    public OfferType[] getOfferTypeValues() {
        return OfferType.values();
    }
    
    public UsageType[] getUsageTypeValues() {
        return UsageType.values();
    }
    
    public String search() {
        return "search?includeViewParams=true&faces-redirect=true";
    }
    
    public List<BasicInsertion> getQuery()  {
        return new ArrayList<BasicInsertion>();
    }

    public UsageType getUsage() {
        return usage;
    }

    public void setUsage(UsageType usage) {
        this.usage = usage;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public OfferType getOffer() {
        return offer;
    }

    public void setOffer(OfferType offer) {
        this.offer = offer;
    }
}
