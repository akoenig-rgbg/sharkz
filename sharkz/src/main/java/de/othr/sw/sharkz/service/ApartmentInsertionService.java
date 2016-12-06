package de.othr.sw.sharkz.service;

import de.othr.sw.sharkz.entity.Address;
import de.othr.sw.sharkz.entity.ApartmentInsertion;
import de.othr.sw.sharkz.entity.type.ApartmentType;
import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.entity.type.HeatingType;
import de.othr.sw.sharkz.entity.type.OfferType;
import java.io.File;
import java.util.List;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

public class ApartmentInsertionService extends ServicePrototype {
    
    @Transactional(TxType.REQUIRED)
    public void createApartmentInsertion(ApartmentType type, boolean lift,
            long pricePerMonth, OfferType offerType, String description,
            Address address, long price, List<File> images, Customer vendor,
            int livingArea, int rooms, HeatingType heating,
            boolean guestToilette, boolean basement, boolean kitchen,
            boolean newBuild, boolean garage, boolean steplessEntry) {
        
        ApartmentInsertion apIns = new ApartmentInsertion(type, lift,
                pricePerMonth, offerType, description, address, price, images,
                vendor, livingArea, rooms, heating, guestToilette, basement,
                kitchen, newBuild, garage, steplessEntry);
        
        em.persist(apIns);
    }
    
    public ApartmentInsertion findApartmentInsertion(long id) {
        return em.find(ApartmentInsertion.class, id);
    }
}
