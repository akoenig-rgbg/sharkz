package de.othr.sw.sharkz.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Customer extends Account {
    
    // Attributes
    private String firstName;
    private String lastName;
    private String phoneNumber;
    
    private BankingData bankingData;
    
    @OneToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private Inbox inbox;

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Insertion> wishList;
    
    @OneToMany(mappedBy="vendor", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private List<Insertion> insertions;

    public Customer() {
        super();
        this.inbox = new Inbox();
    }
    
    public void addMessage(Message message) {
        List<Message> messages = this.inbox.getMessages();
        messages.add(message);
        this.inbox.setMessages(messages);
    }
    
    // Getter & Setter
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Insertion> getWishList() {
        return wishList;
    }

    public void setWishList(List<Insertion> wishList) {
        this.wishList = wishList;
    }

    public List<Insertion> getInsertions() {
        return insertions;
    }

    public void setInsertions(List<Insertion> insertions) {
        this.insertions = insertions;
    }

    public BankingData getBankingData() {
        return bankingData;
    }

    public void setBankingData(BankingData bankingData) {
        this.bankingData = bankingData;
    }

    public Inbox getInbox() {
        return inbox;
    }

    public void setInbox(Inbox inbox) {
        this.inbox = inbox;
    }
}
