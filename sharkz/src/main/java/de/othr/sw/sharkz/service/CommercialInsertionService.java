package de.othr.sw.sharkz.service;

import de.othr.sw.sharkz.entity.Address;
import de.othr.sw.sharkz.entity.CommercialInsertion;
import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.entity.type.CommercialType;
import de.othr.sw.sharkz.entity.type.OfferType;
import java.io.File;
import java.util.List;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

public class CommercialInsertionService extends ServicePrototype {
    @Transactional(TxType.REQUIRED)
    public void createCommercialInsertion(long pricePerMonth,
            OfferType offerType, String description, Address address,
            long price, List<File> images, Customer vendor, CommercialType type,
            int area, boolean aircon, boolean heavyCurrent) {
        
        CommercialInsertion comIn = new CommercialInsertion(pricePerMonth,
                offerType, description, address, price, images, vendor, type,
                area, aircon, heavyCurrent);
        
        em.persist(comIn);
    }
    
    public CommercialInsertion findCommercialInsertion(long id) {
        return em.find(CommercialInsertion.class, id);
    }
}

