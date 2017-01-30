package de.othr.sw.sharkz.service.web.commercialTransaction;

import de.othr.sw.sharkz.entity.Order;
import de.othr.sw.sharkz.service.SearchService;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jws.WebService;

@WebService
@RequestScoped
public class CommercialTransactionService implements Serializable {
    @Inject private SearchService searchService;
    
    /**
     * Retrieve a list of orders containing commercial insertions from Sharkz.
     * 
     * Cast insertion via:
     * CommercialInsertion ins = (CommercialInsertion) order.getInsertion();
     * @param amount the amount of insertions wanted
     * @return a list with the most recent insertions
     */
    public List<Order> getCommercialInsertions(int amount) {
        List<Order> list = searchService.fetchBusiness(amount);
        
        return list;
    }
}
