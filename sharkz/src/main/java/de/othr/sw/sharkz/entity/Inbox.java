package de.othr.sw.sharkz.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Inbox extends EntityPrototype {
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Message> messages;
    
    public Inbox() {
        super();
        this.messages = new ArrayList<>();
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
