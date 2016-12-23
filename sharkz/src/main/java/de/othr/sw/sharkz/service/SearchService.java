package de.othr.sw.sharkz.service;

import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.entity.type.OfferType;
import de.othr.sw.sharkz.entity.type.UsageType;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

@RequestScoped
public class SearchService extends ServicePrototype implements Serializable {
    private List<Insertion> insertions;
    
    private OfferType offerType;
    private UsageType usageType;
    private String location;
    
    public List<Insertion> searchInsertions(UsageType usage, OfferType offer,
            String location) {
        
        Query q = em.createNativeQuery("SELECT * FROM APP.BASICINSERTION WHERE"
                + "TOWN=" + location + ";", Insertion.class);
        
        return q.getResultList();
    }

    public List<Insertion> getInsertions() {
        return insertions;
    }

    public void setInsertions(List<Insertion> insertions) {
        this.insertions = insertions;
    }

    public OfferType getOfferType() {
        return offerType;
    }

    public void setOfferType(OfferType offerType) {
        this.offerType = offerType;
    }

    public UsageType getUsageType() {
        return usageType;
    }

    public void setUsageType(UsageType usageType) {
        this.usageType = usageType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    
}
