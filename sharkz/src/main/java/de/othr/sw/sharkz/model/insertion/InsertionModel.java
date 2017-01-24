package de.othr.sw.sharkz.model.insertion;

import de.othr.sw.sharkz.entity.Account;
import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.model.account.AccountModel;
import de.othr.sw.sharkz.service.AccountService;
import de.othr.sw.sharkz.service.InsertionService;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ViewScoped
@Named("insertion")
public class InsertionModel implements Serializable {
    private Insertion insertion;
    
    // GET parameters
    private long insertionId;
    
    // Models & Services
    @Inject InsertionService insertionService;
    @Inject ContactModel contactModel;
    @Inject AccountModel accountModel;
    @Inject AccountService accountService;
    
    public void loadInsertion() {
        this.insertion = insertionService.getInsertion(insertionId);
    }
    
    
    public String contact() {
        contactModel.setInsertion(insertion);
        
        return "contact";
    }
    
    public String wishlist() {
        if (accountModel.isIsLoggedIn()) {
            Account acc = accountModel.getUser();
            
            String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("insertion_id");
            
            System.out.println("INsertion ID: " + id);
            System.out.println("Insertion itself: " + insertion);
            
            if (acc instanceof Customer) {
                Customer c = (Customer) acc;
                accountService.addToWishlist(c, insertion);
            }
        }
        
        return "insertion?includeViewParams=true";
    }
    
    // Getter & Setter
    public Insertion getInsertion() {
        return insertion;
    }

    public void setInsertion(Insertion insertion) {
        this.insertion = insertion;
    }

    public long getInsertionId() {
        return insertionId;
    }

    public void setInsertionId(long insertionId) {
        this.insertionId = insertionId;
    }
}
