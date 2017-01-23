package de.othr.sw.sharkz.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Inbox extends EntityPrototype {
    @OneToMany
    private List<Message> messages;
    
    public Inbox() {
        super();
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
