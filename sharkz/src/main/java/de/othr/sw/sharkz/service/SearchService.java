package de.othr.sw.sharkz.service;

import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.entity.Insertion;
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
    
    public List<Order> search(OfferType offer, UsageType usage,
            String location) {
        
        List<HouseType> types = HouseType.getTypesOf(usage);

        TypedQuery<Order> q = em.createQuery(
                "SELECT ord FROM Order AS ord WHERE ord.insertion.offerType"
                        + "= :offer AND ord.insertion.address.town = :location"
                        + " AND ord.insertion.houseType IN :types",
                Order.class)
                .setParameter("types", types)
                .setParameter("location", location)
                .setParameter("offer", offer);
        
        return q.getResultList();
    }
    
    public List<Order> search(UsageType usage) {
        List<HouseType> types = HouseType.getTypesOf(usage);
        
        TypedQuery<Order> q = em.createQuery(
                "SELECT ord FROM Order AS ord WHERE ord.insertion.houseType "
                        + "IN :types",
                Order.class)
                .setParameter("types", types);
        
        return q.getResultList();
    }
    
    public List<Order> search(String location) {
        TypedQuery<Order> q = em.createQuery(
                "SELECT ord FROM Order AS ord WHERE ord.insertion.address.town"
                        + "= :location",
                Order.class)
                .setParameter("location", location);
        
        return q.getResultList();
    }
    
    public List<Insertion> getInsertionsByCustomer(Customer customer) {
        
        TypedQuery<Insertion> q = em.createQuery(
                "SELECT ins FROM Insertion AS ins WHERE ins.vendor = :customer",
                Insertion.class)
                .setParameter("customer", customer);
        
        return q.getResultList();
    }
    
    public List<Order> fetchLuxury(int amount) {
        
        TypedQuery<Order> q = em.createQuery(
                "SELECT ord FROM Order AS ord WHERE ord.insertion.offerType "
                        + "= :offer ORDER BY ord.insertion.price DESC",
                Order.class)
                .setParameter("offer", OfferType.PURCHASE)
                .setMaxResults(amount);
        
        return q.getResultList();
    }
    
    public List<Order> fetchBusinessOrders(int amount) {
        
        List<HouseType> types = HouseType.getTypesOf(UsageType.COMMERCIAL);
        
        TypedQuery<Order> q = em.createQuery(
                "SELECT ord FROM Order AS ord WHERE "
                        + "ord.insertion.houseType IN :types",
                Order.class)
                .setParameter("types", types)
                .setMaxResults(amount);
        
        return q.getResultList();
    }
    
    public List<Insertion> fetchBusiness(int amount) {
        
        List<HouseType> types = HouseType.getTypesOf(UsageType.COMMERCIAL);
        
        TypedQuery<Insertion> q = em.createQuery(
                "SELECT ord.insertion FROM Order AS ord WHERE "
                        + "ord.insertion.houseType IN :types",
                Insertion.class)
                .setParameter("types", types)
                .setMaxResults(amount);
        
        return q.getResultList();
    }
    
    public List<Insertion> fetchNewspaper(int amount) {
        TypedQuery<Insertion> q = em.createQuery(
                "SELECT ord.insertion FROM Order AS ord WHERE "
                        + "ord.publishInNewspaper = :newspaper",
                Insertion.class)
                .setParameter("newspaper", true)
                .setMaxResults(amount);
        
        return q.getResultList();
    }
            
}
