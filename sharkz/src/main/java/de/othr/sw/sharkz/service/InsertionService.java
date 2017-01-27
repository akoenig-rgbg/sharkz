package de.othr.sw.sharkz.service;

import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.entity.Order;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

@Named
@RequestScoped
public class InsertionService extends ServicePrototype implements Serializable {
    
    @Inject private AccountService accountService;
    
    @Transactional(TxType.REQUIRED)
    public long createInsertion(Insertion in, long userId) {
        Customer c = em.find(Customer.class, userId);
        
        in.setVendor(c);
        
        em.persist(in);
        em.flush();
        
        return in.getID();
    }
    
    @Transactional(TxType.REQUIRED)
    public void deleteInsertion(long insertionId) {
        Insertion in = em.find(Insertion.class, insertionId);

        // Delete order if insertion was published
        Query q = em.createQuery(
                "DELETE FROM Order o WHERE o.insertion = :insertion")
                .setParameter("insertion", in);
        
        q.executeUpdate();
        
        // Delete insertion from all wishlists which contained it
        TypedQuery<Customer> u = em.createQuery(
                "SELECT cust FROM Customer AS cust WHERE :insertion MEMBER OF "
                        + "cust.wishList",
                Customer.class)
                .setParameter("insertion", in);
        
        for (Object c : u.getResultList()) {
            Customer cust = (Customer) c;
            
            cust.getWishList().remove(in);
            em.merge(cust);
        }
        
        Query v = em.createQuery("UPDATE Message m SET m.insertion = NULL "
                + "WHERE m.insertion = :insertion")
                .setParameter("insertion", in);
        
        v.executeUpdate();
        
        // Delete insertion
        em.remove(in);
        em.flush();
    }
    
    public Insertion findInsertion(long insertionId) {
        return em.find(Insertion.class, insertionId);
    }
    
    @Transactional(TxType.REQUIRED)
    public void setInsertionVendor(long insertionId, long vendorId) {
        Insertion in = em.find(Insertion.class, insertionId);
        Customer c = em.find(Customer.class, vendorId);
        
        in.setVendor(c);
        em.merge(in);
        em.flush();
    }
    
    @Transactional(TxType.REQUIRED)
    public void setInsertionImages(long insertionId, List<byte[]> images) {
        Insertion in = em.find(Insertion.class, insertionId);
        
        in.setImages(images);
        em.merge(in);
        em.flush();
    }
    
    @Transactional(TxType.REQUIRED)
    public long publishInsertion(long insertionId, int duration) {
        Order order = new Order();
        
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, duration);
        
        Insertion insertion = em.find(Insertion.class, insertionId);
        
        order.setCustomer(insertion.getVendor());
        order.setInsertion(insertion);
        order.setStartDate(new Date());
        order.setEndDate(c.getTime());
        
        em.persist(order);
        em.flush();
        
        return insertion.getID();
    }
    
    public boolean isPublic(Insertion in) {
        in = em.find(Insertion.class, in.getID());
        
        TypedQuery<Order> q = em.createQuery(
                "SELECT ord FROM Order AS ord WHERE ord.insertion = :ins",
                Order.class)
                .setParameter("ins", in);
        
        return q.getResultList().size() == 1;
    }
}
