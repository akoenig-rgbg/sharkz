package de.othr.sw.sharkz.service;

import de.othr.sw.sharkz.entity.BasicInsertion;
import de.othr.sw.sharkz.entity.type.OfferType;
import de.othr.sw.sharkz.entity.type.UsageType;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@RequestScoped
public class SearchService {
    private List<BasicInsertion> insertions;
    
    private OfferType offerType;
    private UsageType usageType;
    private String location;
    
    @PersistenceContext
    EntityManager em;
    
    public List<BasicInsertion> searchInsertions(UsageType usage, OfferType offer,
            String location) {
        
        Query q = em.createNativeQuery("SELECT * FROM APP.BASICINSERTION WHERE"
                + "TOWN=" + location + ";", BasicInsertion.class);
        
        return q.getResultList();
    }

    public List<BasicInsertion> getInsertions() {
        return insertions;
    }

    public void setInsertions(List<BasicInsertion> insertions) {
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
