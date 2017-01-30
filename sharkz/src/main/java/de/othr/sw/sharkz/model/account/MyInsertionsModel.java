package de.othr.sw.sharkz.model.account;

import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.service.InsertionService;
import de.othr.sw.sharkz.service.SearchService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ViewScoped
@Named("myInsertions")
public class MyInsertionsModel implements Serializable {
    
    // Attributes
    private List<Insertion> insertions;
    private boolean hasNoResults = false;
    
    // Models & Services
    @Inject private AccountModel accountModel;
    @Inject private SearchService searchService;
    @Inject private InsertionService insertionService;
    
    @PostConstruct
    public void init() {
        insertions = searchService.getInsertionsByCustomer(
                (Customer) accountModel.getUser());
        
        if (insertions == null || insertions.isEmpty())
            hasNoResults = true;
    }

    public String edit(long insertionId) {
        // TODO: Create edit insertion page or use create page
        
        return null;
    }
    
    public String delete(Insertion ins) {
        insertionService.deleteInsertion(ins.getID());
        
        //messageModel.displayMessage("Inserat wurde gelöscht!");
        
        return "myInsertions";
    }
    
    public List<Insertion> getInsertions() {
        return insertions;
    }

    public void setInsertions(List<Insertion> insertions) {
        this.insertions = insertions;
    }

    public boolean isHasNoResults() {
        return hasNoResults;
    }

    public void setHasNoResults(boolean hasNoResults) {
        this.hasNoResults = hasNoResults;
    }
    
    
}
