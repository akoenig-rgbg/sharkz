package de.othr.sw.sharkz.model.account;

import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.service.AccountService;
import de.othr.sw.sharkz.service.InsertionService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ViewScoped
@Named("wishlist")
public class WishlistModel implements Serializable {
    
    // Attributes
    private List<Insertion> wishlist;
    
    // Models & Services
    @Inject private AccountModel accountModel;
    @Inject private AccountService accountService;
    @Inject private InsertionService insertionService;
    
    @PostConstruct
    public void init() {
        wishlist = accountService.findCustomer(
                accountModel.getUser().getID()).getWishList();
    }

    public String delete(Insertion ins) {
        insertionService.deleteInsertion(ins.getID());
        
        return null;
    }
    
    public List<Insertion> getInsertions() {
        return wishlist;
    }

    public void setInsertions(List<Insertion> insertions) {
        this.wishlist = insertions;
    }
    
    
}
