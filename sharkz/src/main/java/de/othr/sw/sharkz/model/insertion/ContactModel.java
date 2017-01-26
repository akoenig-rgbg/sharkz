package de.othr.sw.sharkz.model.insertion;

import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.entity.Message;
import de.othr.sw.sharkz.model.account.AccountModel;
import de.othr.sw.sharkz.service.AccountService;
import de.othr.sw.sharkz.service.InsertionService;
import java.io.Serializable;
import java.util.Date;
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
        
        if (insertion.getVendor() instanceof Customer) {
            //accountService.addMessage((Customer) insertion.getVendor(),
            //        message);
            
            accountService.addMessage((Customer) accountModel.getUser(),
                    insertion.getVendor(), insertion, message);
        }
        
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
