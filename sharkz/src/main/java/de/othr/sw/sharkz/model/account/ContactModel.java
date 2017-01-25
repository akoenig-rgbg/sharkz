package de.othr.sw.sharkz.model.account;

import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.entity.Message;
import de.othr.sw.sharkz.model.insertion.InsertionModel;
import de.othr.sw.sharkz.service.AccountService;
import de.othr.sw.sharkz.service.InsertionService;
import java.io.Serializable;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ViewScoped
@Named("contact")
public class ContactModel implements Serializable {
    private Insertion insertion;
    
    // Form attributes
    private String title;
    private String content;
    
    // Models & Services
    @Inject AccountService accountService;
    @Inject AccountModel accountModel;
    @Inject InsertionService insertionService;
    
    public void loadInsertion() {
        insertion = insertionService.getInsertion(Long.parseLong(
                FacesContext.getCurrentInstance().getExternalContext()
                        .getRequestParameterMap().get("insertion_id")));
        
    }

    public String sendMessage() {
        System.out.println("Inserat zum Senden: " + insertion);
        
        Message message = new Message();
        
        message.setInsertion(insertion);
        message.setSender(accountModel.getUser());
        message.setSendDate(new Date());
        message.setTitle(title);
        message.setContent(content);
        
        insertion.getVendor().addMessage(message);
        
        accountService.updateAccount(insertion.getVendor());
        
       // insertionModel.setInsertionId(insertion.getID());
        
        return "insertion";
    }
    
    // Getter & Setter
    public Insertion getInsertion() {
        return insertion;
    }

    public void setInsertion(Insertion insertion) {
        this.insertion = insertion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
