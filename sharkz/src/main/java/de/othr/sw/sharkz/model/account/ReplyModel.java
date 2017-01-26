package de.othr.sw.sharkz.model.account;

import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.entity.Message;
import de.othr.sw.sharkz.service.AccountService;
import java.io.Serializable;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ViewScoped
@Named("reply")
public class ReplyModel implements Serializable {
    
    // Attributes
    private Message message;
    
    // GET parameters
    private long messageId;
    
    // Form attributes
    private String title;
    private String content;
    
    // Models & Services
    @Inject private AccountService accountService;
    @Inject private AccountModel accountModel;
    
    public void loadInsertion() {
        message = accountService.findMessage(Long.parseLong(
                FacesContext.getCurrentInstance().getExternalContext()
                        .getRequestParameterMap().get("message_id")));
    }
    
    public String sendMessage() {
        Message msg = new Message();
        
        msg.setSendDate(new Date());
        msg.setTitle(title);
        msg.setContent(content);
        
        accountService.addMessage(accountModel.getUser().getID(),
                message.getSender().getID(), message.getInsertion().getID(),
                msg);
        
        return "inbox";
    }
    
    // Getter & Setter
    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
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
