package de.othr.sw.sharkz.service;

import de.othr.sw.sharkz.entity.Account;
import de.othr.sw.sharkz.entity.CommercialInsertion;
import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.entity.LivingInsertion;
import de.othr.sw.sharkz.entity.Order;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

@Named
@RequestScoped
public class InsertionService extends ServicePrototype implements Serializable {
    
    @Transactional(TxType.REQUIRED)
    public long createInsertion(Insertion in) {
        em.persist(in);
        em.flush();
        
        return in.getID();
    }
    
    @Transactional(TxType.REQUIRED)
    public void deleteInsertion(Insertion in) {
        Query q = em.createQuery(
                "DELETE FROM Order o WHERE o.insertion = :insertion")
                .setParameter("insertion", in);
        
        q.executeUpdate();
        
        em.remove(em.merge(in));
        em.flush();
    }
    
    public Insertion getInsertion(long id) {
        Query q = em.createNativeQuery("SELECT DTYPE FROM Insertion WHERE ID = " + id);
        
        if (((String) q.getSingleResult()).equalsIgnoreCase("CommercialInsertion"))
            return em.find(CommercialInsertion.class, id);
        else
            return em.find(LivingInsertion.class, id);
    }

    @Transactional(TxType.REQUIRED)
    public void deleteAllInsertions() {
        Query q = em.createNativeQuery("DELETE * FROM Insertion");
        q.executeUpdate();
    }
    
    @Transactional(TxType.REQUIRED)
    public long publishInsertion(Insertion insertion, int duration) {
        Order order = new Order();
        
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, duration);
        
        order.setCustomer(insertion.getVendor());
        order.setInsertion(insertion);
        order.setStartDate(new Date());
        order.setEndDate(c.getTime());
        
        em.persist(order);
        em.flush();
        
        return insertion.getID();
    }
}
