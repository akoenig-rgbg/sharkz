package de.othr.sw.sharkz.service.web.newspaperTransaction;

import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.service.SearchService;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jws.WebService;

@WebService
@RequestScoped
public class NewspaperTransactionService implements Serializable {
    @Inject private SearchService searchService;
    
    /**
     * Retrieve a list of insertions to be published in the newspaper from Sharkz.
     * 
     * Cast insertion via:
     * CommercialInsertion ins = (CommercialInsertion) insertion;
     * 
     * or
     * 
     * LivingInsertion ins = (LivingInsertion) insertion();
     * 
     * Don't forget to check: 
     * if (insertion instanceof LivingInsertion)
     *     do...
     * 
     * @param amount the amount of insertions wanted
     * @return a list with the most recent insertions
     */
    public List<Insertion> getCommercialInsertions(int amount) {
        List<Insertion> list = searchService.fetchNewspaper(amount);
        
        return list;
    }
}
