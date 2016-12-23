package de.othr.sw.sharkz.model;

import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.entity.type.HouseType;
import de.othr.sw.sharkz.entity.type.OfferType;
import de.othr.sw.sharkz.entity.type.UsageType;
import de.othr.sw.sharkz.service.SearchService;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value="search")
@RequestScoped
public class SearchModel {
    
    private UsageType usage;
    private String location;
    private OfferType offer;
    
    @Inject
    private SearchService searchService;
    
    public OfferType[] getOfferTypeValues() {
        return OfferType.values();
    }
    
    public HouseType[] getHouseTypeValues() {
        return HouseType.values();
    }
    
    public UsageType[] getUsageTypeValues() {
        return UsageType.values();
    }
    
    public List<Insertion> search() {
        //if (location != null)
          //  return searchService.searchInsertions(null, null, location);
        
        return null;
    }
    
    public List<Insertion> getQuery()  {
        return new ArrayList<Insertion>();
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
