package de.othr.sw.sharkz.model.insertion;

import de.othr.sw.sharkz.model.account.AccountModel;
import de.othr.sw.sharkz.service.InsertionService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

@ViewScoped
@Named(value="publish")
public class PublishModel implements Serializable {
    
    // Form attributes
    private int duration = 1;
    
    private boolean successfulValidation = false;
    
    // Services & Models
    @Inject private InsertionService insertionService;
    @Inject private AccountModel accountModel;
    @Inject private InsertionModel insertionModel;
    
    // GET parameters
    private long insertionId;
    
    public PublishModel() {
        super();
    }

    public void loadInsertion() {
        if (FacesContext.getCurrentInstance().isPostback())
            return;
        
        insertionModel.loadInsertion(insertionId);
    }
    
    public void validatePublishment(FacesContext ctx, UIComponent comp,
            Object value) {
        
        try {
        System.out.println("accountModel: " + accountModel.getUser().getID());
        
        System.out.println("insertion: "
                + insertionModel.getInsertion().getVendor().getID());
        } catch (NullPointerException e) {
            System.out.println("Nullpointerexception beim Vendor/User");
        }
        
        List<FacesMessage> msgs = new ArrayList<>();
        
        // validate input
        if (duration <= 0)
            msgs.add(new FacesMessage("Die Dauer der "
                    + "Veröffentlichung muss <= 0 sein!"));
        
        if (insertionModel.getInsertion() == null)
            msgs.add(new FacesMessage("Das Inserat mit ID " + insertionId
                    + "existiert nicht!"));
        
        if (!accountModel.isIsLoggedIn())
            msgs.add(new FacesMessage("Sie müssen eingeloggt sein, um das "
                    + "Inserat zu veröffentlichen!"));
        
        else if (!accountModel.getUser().equals(
                insertionModel.getInsertion().getVendor()))
            msgs.add(new FacesMessage("Sie können nur Ihre eigenen Inserate "
                    + "veröffentlichen!"));
        
        if (!msgs.isEmpty())
            throw new ValidatorException(msgs);
        
        else
            successfulValidation = true;
    }
    
    public String publishInsertion() {
        // publish insertion
        insertionService.publishInsertion(insertionModel.getInsertion().getID(),
                duration);
       
        // Propagate GET parameters
        insertionModel.setInsertion(insertionModel.getInsertion());
        
        // redirect
        if (successfulValidation)
            return "success";
        
        return "failure";
    }
    
    // Getter & Setter
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getInsertionId() {
        return insertionId;
    }

    public void setInsertionId(long insertionId) {
        this.insertionId = insertionId;
    }

    public InsertionModel getInsertionModel() {
        return insertionModel;
    }

    public void setInsertionModel(InsertionModel insertionModel) {
        this.insertionModel = insertionModel;
    }
}
