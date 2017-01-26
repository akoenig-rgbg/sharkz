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
        
        msg.setSender(accountModel.getUser());
        msg.setSendDate(new Date());
        msg.setInsertion(message.getInsertion());
        msg.setTitle(title);
        msg.setContent(content);
        
        Customer c = ((Customer) message.getSender());
        c.getInbox().getMessages().add(msg);
        
        accountService.updateAccount(c);
        
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
