package de.othr.sw.sharkz.service;

import de.othr.sw.sharkz.entity.Insertion;
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
        return em.find(Insertion.class, id);
    }

    @Transactional(TxType.REQUIRED)
    public void deleteAllInsertions() {
        Query q = em.createNativeQuery("DELETE * FROM Insertion");
        q.executeUpdate();
    }
}
