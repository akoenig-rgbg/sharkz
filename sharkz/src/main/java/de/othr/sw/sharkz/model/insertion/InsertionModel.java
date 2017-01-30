package de.othr.sw.sharkz.model.insertion;

import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.model.account.AccountModel;
import de.othr.sw.sharkz.service.AccountService;
import de.othr.sw.sharkz.service.InsertionService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
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
    }
    
    public void loadInsertion(long id) {
        insertion = insertionService.findInsertion(id);
        importantAttributes = insertion.getImportantAttributes();
        size = insertion.getImages().size();
    }
    
    public void addToWishlist(Insertion in) {
        accountService.addToWishlist(accountModel.getUser().getID(), in);
    }
    
    public String contact() {
        contactModel.setInsertion(insertion);
        
        return "contact";
    }
    
    public String changeFeaturedImage(int id) {
        this.featImgId = id;
        
        return "";
    }
    
    public void validatePublishment(FacesContext ctx, UIComponent comp,
            Object value) {
        
        try {
        System.out.println("accountModel: " + accountModel.getUser().getID());
        
        System.out.println("insertion: "
                + insertion.getVendor().getID());
        } catch (NullPointerException e) {
            System.out.println("Nullpointerexception beim Vendor/User");
        }
        
        List<FacesMessage> msgs = new ArrayList<>();
        
        // validate input
        if (duration <= 0)
            msgs.add(new FacesMessage("Die Dauer der "
                    + "Veröffentlichung muss <= 0 sein!"));
        
        if (insertion == null)
            msgs.add(new FacesMessage("Das Inserat mit ID " + insertionId
                    + "existiert nicht!"));
        
        if (!accountModel.isIsLoggedIn())
            msgs.add(new FacesMessage("Sie müssen eingeloggt sein, um das "
                    + "Inserat zu veröffentlichen!"));
        
        else if (!accountModel.getUser().equals(
                insertion.getVendor()))
            msgs.add(new FacesMessage("Sie können nur Ihre eigenen Inserate "
                    + "veröffentlichen!"));
        
        if (!msgs.isEmpty())
            throw new ValidatorException(msgs);
        
        else
            successfulValidation = true;
    }
    
    public String publishInsertion() {
        // publish insertion
        insertionService.publishInsertion(insertion.getID(), duration, false);
        
        isPublishment = false;
        
        // redirect
        if (successfulValidation)
            return "success";
        
        return "failure";
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
    
    
}
