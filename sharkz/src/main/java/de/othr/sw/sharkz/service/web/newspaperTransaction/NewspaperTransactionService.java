package de.othr.sw.sharkz.service.web.newspaperTransaction;

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
public class NewspaperTransactionService implements Serializable {
    @Inject private SearchService searchService;
    
    /**
     * Retrieve a list of NewspaperTransaction objects containg insertions
     * to be published in the newspaper from Sharkz.
     * 
     * The unit of price is Euros.
     *
     * @param amount the amount of insertions wanted
     * @return a list with the most recent insertions
     */
    public List<NewspaperTransaction> getNewspaperInsertions(int amount) {
        List<Insertion> list = searchService.fetchNewspaper(amount);
        
        List<NewspaperTransaction> result = new ArrayList<>();
        
        for (Insertion i : list) {
            NewspaperTransaction t = new NewspaperTransaction();
            
            t.setAddress(i.getAddress());
            t.setDescription(i.getDescription());
            t.setHouseType(i.getHouseType().getLabel());
            t.setOfferType(i.getOfferType().getLabel());
            t.setPrice(i.getPrice());
            t.setTitle(i.getTitle());
            
            result.add(t);
        }
        
        return result;
    }
}
