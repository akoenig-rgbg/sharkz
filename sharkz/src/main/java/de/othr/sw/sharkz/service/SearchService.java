package de.othr.sw.sharkz.service;

import de.othr.sw.sharkz.entity.CommercialInsertion;
import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.entity.LivingInsertion;
import de.othr.sw.sharkz.entity.type.OfferType;
import de.othr.sw.sharkz.entity.type.UsageType;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.persistence.TypedQuery;

@RequestScoped
public class SearchService extends ServicePrototype implements Serializable {
    private List<Insertion> insertions;
    
    private OfferType offerType;
    private UsageType usageType;
    private String location;
    
    public List<Insertion> search(OfferType offer,
            String location) {

         TypedQuery<Insertion> q = em.createQuery(
                "SELECT ins FROM Insertion AS ins WHERE ins.offerType "
                        + "= :offer AND ins.address.town = :location",
                Insertion.class)
                .setParameter("offer", offer)
                .setParameter("location", location);
        
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
