package de.othr.sw.sharkz.service;

import de.othr.sw.sharkz.entity.BasicInsertion;
import de.othr.sw.sharkz.entity.type.OfferType;
import de.othr.sw.sharkz.entity.type.UsageType;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RequestScoped
public class SearchService {
    private List<BasicInsertion> insertions;
    
    private OfferType offerType;
    private UsageType usageType;
    private String location;
    
    @PersistenceContext
    EntityManager em;
    
    public String query() {
        String answer;
        
        //Query q = em.createQuery(, resultClass)
        
        //return answer;
        
        return "Test";
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
