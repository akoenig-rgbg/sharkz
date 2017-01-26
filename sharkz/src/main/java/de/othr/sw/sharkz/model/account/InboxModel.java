package de.othr.sw.sharkz.model.account;

import de.othr.sw.sharkz.entity.Account;
import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.entity.Message;
import de.othr.sw.sharkz.service.AccountService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ViewScoped
@Named("inbox")
public class InboxModel implements Serializable {
    
    private List<Message> messages;
    private Customer customer;
    
    private long messageId;
    
    // Models & Services
    @Inject private AccountModel accountModel;
    @Inject private AccountService accountService;
    
    @PostConstruct
    public void init() {
        if (!accountModel.isIsLoggedIn())
            return;
        
        customer = accountService.findCustomer(accountModel.getUser().getID());
        
        messages = customer.getInbox().getMessages();
        
        System.out.println("Nachrichten: " + messages);
    }
    
    public String delete(Message message) {
        accountService.deleteMessage(accountModel.getUser().getID(), message);
        
        return "inbox";
    }
    
    public String reply(Message message) {
        this.messageId = message.getID();
        
        return "reply";
    }
    
    // Getter & Setter
    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
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
}
