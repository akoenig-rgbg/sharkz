package de.othr.sw.sharkz.model.account;

import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.service.AccountService;
import de.othr.sw.sharkz.service.InsertionService;
import java.io.Serializable;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ViewScoped
@Named("wishlist")
public class WishlistModel implements Serializable {
    
    // Attributes
    private Set<Insertion> wishlist;
    private boolean hasNoResults = false;
    
    // Models & Services
    @Inject private AccountModel accountModel;
    @Inject private AccountService accountService;
    @Inject private InsertionService insertionService;
    
    @PostConstruct
    public void init() {
        wishlist = accountService.findCustomer(
                accountModel.getUser().getID()).getWishList();
        
        if (wishlist == null || wishlist.isEmpty())
            hasNoResults = true;
    }

    /**
     * Deletes an insertion from the wishlist
     * @param ins
     * @return 
     */
    public String delete(Insertion ins) {
        insertionService.deleteInsertion(ins.getID());
        
        return "wishlist";
    }
    
    // Getter & Setter
    public Set<Insertion> getInsertions() {
        return wishlist;
    }

    public void setInsertions(Set<Insertion> insertions) {
        this.wishlist = insertions;
    }

    public boolean isHasNoResults() {
        return hasNoResults;
    }

    public void setHasNoResults(boolean hasNoResults) {
        this.hasNoResults = hasNoResults;
    }
    
    
}
