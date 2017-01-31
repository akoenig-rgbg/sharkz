package de.othr.sw.sharkz.model.insertion;

import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.model.account.AccountModel;
import de.othr.sw.sharkz.service.AccountService;
import de.othr.sw.sharkz.service.InsertionService;
import java.io.Serializable;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ViewScoped
@Named("insertion")
public class InsertionModel implements Serializable {
    private Insertion insertion;
    private boolean successfulValidation = false;
    private boolean isPublic = true;
    
    // GET parameters
    private long insertionId;
    private boolean isPublishment = false;
    
    // Form attributes
    private int duration = 1;

    private int featImgId;
    private int size;
    private Map<String, String> importantAttributes;
    
    // Wishlist attributes
    private boolean isOnWishlist;
    private String star;
    
    // Models & Services
    @Inject InsertionService insertionService;
    @Inject ContactModel contactModel;
    @Inject AccountModel accountModel;
    @Inject AccountService accountService;
    
    public void loadInsertion() {
        if (FacesContext.getCurrentInstance().isPostback())
            return;
        
        insertion = insertionService.findInsertion(insertionId);
        importantAttributes = insertion.getImportantAttributes();
        size = insertion.getImages().size();
        
        if (!insertionService.isPublic(insertion))
            this.isPublic = false;
        
        if (accountModel.isIsLoggedIn()) {
            isOnWishlist = insertionService.isOnWishlist(insertion.getID(),
                    accountModel.getUser().getID());
            
            setStar();
        }
    }
    
    public void loadInsertion(long id) {
        insertion = insertionService.findInsertion(id);
        importantAttributes = insertion.getImportantAttributes();
        size = insertion.getImages().size();
    }
    
    public void wishlistClick() {
        if (!isOnWishlist) {
            accountService.addToWishlist(accountModel.getUser().getID(), insertion);
            
            FacesContext.getCurrentInstance().addMessage("interaction_form",
                    new FacesMessage("Zum Wunschzettel hinzugefügt!"));
        } else {
        
            accountService.deleteFromWishlist(accountModel.getUser().getID(),
                        insertion);

            FacesContext.getCurrentInstance().addMessage("interaction_form",
                        new FacesMessage("Vom Wunschzettel entfernt!"));
        }
        
        isOnWishlist = !isOnWishlist;
        setStar();
    }
    
    private void setStar() {
        if (isOnWishlist)
            star = "fa fa-star";
        else
            star = "fa fa-star-o";
    }
    
    public String contact() {
        contactModel.setInsertion(insertion);
        
        return "contact";
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

    public boolean isSuccessfulValidation() {
        return successfulValidation;
    }

    public void setSuccessfulValidation(boolean successfulValidation) {
        this.successfulValidation = successfulValidation;
    }

    public boolean isIsPublishment() {
        return isPublishment;
    }

    public void setIsPublishment(boolean isPublishment) {
        this.isPublishment = isPublishment;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }
}
