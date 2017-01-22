package de.othr.sw.sharkz.service;

import de.othr.sw.sharkz.entity.CommercialInsertion;
import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.entity.LivingInsertion;
import de.othr.sw.sharkz.entity.Order;
import de.othr.sw.sharkz.entity.type.HouseType;
import de.othr.sw.sharkz.entity.type.OfferType;
import de.othr.sw.sharkz.entity.type.UsageType;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.persistence.TypedQuery;

@RequestScoped
public class SearchService extends ServicePrototype implements Serializable {
    private List<Insertion> insertions;
    
    public List<Order> search(OfferType offer, UsageType usage,
            String location) {
        
        List<HouseType> types = HouseType.getTypesOf(usage);

        TypedQuery<Order> q = em.createQuery(
                "SELECT ord FROM Order AS ord WHERE ord.insertion.offerType"
                        + "= :offer AND ord.insertion.address.town = :location"
                        + " AND ord.insertion.houseType IN :types",
                Order.class)
                .setParameter("offer", offer)
                .setParameter("location", location)
                .setParameter("types", types);
        
        return q.getResultList();
    }
    
    public List<Order> fetchLuxury(int amount) {
        
        TypedQuery<Order> q = em.createQuery(
                "SELECT ord FROM Order AS ord ORDER BY ord.insertion.price DESC",
                Order.class).setMaxResults(amount);
        
        return q.getResultList();
    }
    
    public List<Insertion> getInsertions() {
        return insertions;
    }

    public void setInsertions(List<Insertion> insertions) {
        this.insertions = insertions;
    }
}
