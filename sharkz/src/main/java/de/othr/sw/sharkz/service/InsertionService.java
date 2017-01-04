package de.othr.sw.sharkz.service;

import de.othr.sw.sharkz.entity.CommercialInsertion;
import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.entity.LivingInsertion;
import java.io.Serializable;
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
        em.merge(in);
        em.remove(in); 
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
}
