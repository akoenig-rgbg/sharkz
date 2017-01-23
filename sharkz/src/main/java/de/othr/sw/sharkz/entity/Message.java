package de.othr.sw.sharkz.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Message extends EntityPrototype {
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendDate;
    
    @OneToOne
    private Account sender;
    
    @OneToOne
    private Order insertion;
    
    private String content;
    
    public Message() {
        super();
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public Order getInsertion() {
        return insertion;
    }

    public void setInsertion(Order insertion) {
        this.insertion = insertion;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
