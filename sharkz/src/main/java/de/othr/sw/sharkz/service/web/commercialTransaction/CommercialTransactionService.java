package de.othr.sw.sharkz.service.web.commercialTransaction;

import de.othr.sw.sharkz.entity.CommercialInsertion;
import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.service.SearchService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jws.WebService;

@WebService
@RequestScoped
public class CommercialTransactionService implements Serializable {
    @Inject private SearchService searchService;
    
    /**
     * Retrieve a list of CommercialTransaction objects from Sharkz.
     * 
     * @param amount the amount of insertions wanted
     * @return a list with the most recent insertions
     */
    public List<CommercialTransaction> getCommercialInsertions(int amount) {
        List<Insertion> insertions = searchService.fetchBusiness(amount);
        
        List<CommercialTransaction> result = new ArrayList<>();
        
        for (Insertion in : insertions) {
            if (in instanceof CommercialInsertion) {
                CommercialInsertion c = (CommercialInsertion) in;
            
                CommercialTransaction t = new CommercialTransaction();

                t.setAddress(c.getAddress());
                t.setAircon(c.isAircon());
                t.setArea(c.getArea());
                t.setDescription(c.getDescription());
                t.setHeavyCurrent(c.isHeavyCurrent());
                t.setHouseType(c.getHouseType().getLabel());
                t.setOfferType(c.getOfferType().getLabel());
                t.setPrice(c.getPrice());
                t.setTitle(c.getTitle());
                t.setVendor(c.getVendor().getFirstName() + " "
                        + c.getVendor().getLastName());
                t.setLink("im-lamport:8080/Sharkz/views/insertion/insertion.xhtml?insertion_id=" + in.getID());
                
                result.add(t);
            }
        }
        
        return result;
    }
}
