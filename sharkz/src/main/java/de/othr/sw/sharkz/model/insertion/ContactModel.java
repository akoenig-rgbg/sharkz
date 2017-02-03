package de.othr.sw.sharkz.model.insertion;

import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.entity.Message;
import de.othr.sw.sharkz.model.account.AccountModel;
import de.othr.sw.sharkz.service.AccountService;
import de.othr.sw.sharkz.service.InsertionService;
import java.io.Serializable;
import java.util.Date;
import javax.faces.application.FacesMessage;
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
        insertion = insertionService.findInsertion(Long.parseLong(
                FacesContext.getCurrentInstance().getExternalContext()
                        .getRequestParameterMap().get("insertion_id")));
        
    }

    /**
     * Sends a message
     * @return 
     */
    public String sendMessage() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        if (!accountModel.isIsLoggedIn())
            ctx.addMessage("contact_form",
                    new FacesMessage("Bitte loggen Sie sich ein, "
                            + "um eine Nachricht zu versenden!"));
            
        if (title == null || title.equals(""))
            ctx.addMessage("contact_form", new FacesMessage(
                    "Bitte geben Sie einen Titel ein!"));
        
        if (content == null || content.equals(""))
            ctx.addMessage("contact_form", new FacesMessage(
                    "Bitte geben Sie eine Nachricht ein!"));
            
        if (!ctx.getMessageList().isEmpty())
            return null;
        
        Message message = new Message();
        
        message.setSendDate(new Date());
        message.setTitle(title);
        message.setContent(content);
        
        accountService.addMessage(accountModel.getUser().getID(),
                insertion.getVendor().getID(), insertion.getID(), message);
        
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
