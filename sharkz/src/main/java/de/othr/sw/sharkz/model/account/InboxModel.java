package de.othr.sw.sharkz.model.account;

import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.entity.Message;
import de.othr.sw.sharkz.service.AccountService;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ViewScoped
@Named("inbox")
public class InboxModel implements Serializable {
    
    private Set<Message> messages;
    private Customer customer;
    
    private long messageId;
    
    private boolean hasNoResults = false;
    
    // Models & Services
    @Inject private AccountModel accountModel;
    @Inject private AccountService accountService;
    
    @PostConstruct
    public void init() {
        if (!accountModel.isIsLoggedIn())
            return;
        
        customer = accountService.findCustomer(accountModel.getUser().getID());
        
        messages = customer.getMessages();
        
        if (messages == null || messages.isEmpty())
            hasNoResults = true;
    }
    
    public String delete(Message message) {
        accountService.deleteMessage(message.getID());
        
        return "inbox";
    }
    
    public String reply(Message message) {
        this.messageId = message.getID();
        
        return "reply";
    }
    
    // Getter & Setter
    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public void setMessageId(long msg) {
        this.messageId = msg;
    }
    
    public long getMessageId() {
        return this.messageId;
    }

    public boolean isHasNoResults() {
        return hasNoResults;
    }

    public void setHasNoResults(boolean hasNoResults) {
        this.hasNoResults = hasNoResults;
    }
    
    
}