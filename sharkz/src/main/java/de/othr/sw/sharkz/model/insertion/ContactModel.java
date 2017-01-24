package de.othr.sw.sharkz.model.insertion;

import de.othr.sw.sharkz.entity.Insertion;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@RequestScoped
@Named("contact")
public class ContactModel {
    
    // Attributes
    private Insertion insertion;
    
    // Form attributes
    private String content;
    
    public ContactModel() {
        super();
    }
    
    // Getter & Setter
    public Insertion getInsertion() {
        return insertion;
    }

    public void setInsertion(Insertion insertion) {
        this.insertion = insertion;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
}
