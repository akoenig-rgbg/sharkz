package de.othr.sw.sharkz.model.insertion;

import de.othr.sw.sharkz.entity.Account;
import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.model.account.AccountModel;
import de.othr.sw.sharkz.service.AccountService;
import de.othr.sw.sharkz.service.InsertionService;
import java.io.Serializable;
import java.util.Map;
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
    
    private int featImgId;
    private int size;
    private Map<String, String> importantAttributes;
    
    // Models & Services
    @Inject InsertionService insertionService;
    @Inject ContactModel contactModel;
    @Inject AccountModel accountModel;
    @Inject AccountService accountService;
    
    public void loadInsertion() {
        if (FacesContext.getCurrentInstance().isPostback())
            return;
        
        insertion = insertionService.getInsertion(insertionId);
        importantAttributes = insertion.getImportantAttributes();
        size = insertion.getImages().size();
    }
    
    public String contact() {
        contactModel.setInsertion(insertion);
        
        return "contact";
    }
    
    public void wishlist(Insertion in) {
        if (accountModel.isIsLoggedIn()) {
            Account acc = accountModel.getUser();
            
            System.out.println("Insertion ID: " + insertionId);
            System.out.println("Insertion itself: " + in);
            
            if (acc instanceof Customer) {
                Customer c = (Customer) acc;
                accountService.addToWishlist(c, in);
            }
        }
    }
    
    public String changeFeaturedImage(int id) {
        this.featImgId = id;
        
        return "";
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

    public int getFeatImgId() {
        return featImgId;
    }

    public void setFeatImgId(int featImgId) {
        this.featImgId = featImgId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Map<String, String> getImportantAttributes() {
        return importantAttributes;
    }

    public void setImportantAttributes(Map<String, String> importantAttributes) {
        this.importantAttributes = importantAttributes;
    }
}
