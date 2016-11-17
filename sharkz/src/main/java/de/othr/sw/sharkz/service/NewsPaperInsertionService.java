package de.othr.sw.sharkz.service;

import de.othr.sw.sharkz.entity.BasicInsertion;
import de.othr.sw.sharkz.entity.NewsPaperInsertion;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

public class NewsPaperInsertionService extends ServicePrototype {
    @Transactional(TxType.REQUIRED)
    public void createNewsPaperInsertion(BasicInsertion ins, boolean premium,
            long pricePerMonth) {
        NewsPaperInsertion npIns = new NewsPaperInsertion(ins, premium,
                pricePerMonth);
        
        em.persist(npIns);
    }
    
    public NewsPaperInsertion findNewsPaperInsertion(long id) {
        return em.find(NewsPaperInsertion.class, id);
    }
}
