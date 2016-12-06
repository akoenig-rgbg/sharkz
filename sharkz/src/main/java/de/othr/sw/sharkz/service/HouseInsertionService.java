package de.othr.sw.sharkz.service;

import de.othr.sw.sharkz.entity.Address;
import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.entity.HouseInsertion;
import de.othr.sw.sharkz.entity.type.HeatingType;
import de.othr.sw.sharkz.entity.type.HouseType;
import de.othr.sw.sharkz.entity.type.OfferType;
import java.io.File;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

public class HouseInsertionService extends ServicePrototype {

    @Transactional(TxType.REQUIRED)
    public void createHouseInsertion(HouseType type, int plotArea,
            long pricePerMonth, OfferType offerType, String description,
            Address address, long price, List<File> images, Customer vendor,
            int livingArea, int rooms, HeatingType heating,
            boolean guestToilette, boolean basement, boolean kitchen,
            boolean newBuild, boolean garage, boolean steplessEntry) {
        
        HouseInsertion houseIns = new HouseInsertion(type, plotArea,
                pricePerMonth, offerType, description, address, price,
                images, vendor, livingArea, rooms, heating, guestToilette,
                basement, kitchen, newBuild, garage, steplessEntry);
        
        em.persist(houseIns);
    }
    
    public HouseInsertion findHouseInsertion(long id) {
        return em.find(HouseInsertion.class, id);
    }
}
