package de.othr.sw.sharkz.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class NewsPaperInsertion extends Product {
    @OneToOne
    private BasicInsertion Insertion;

    public NewsPaperInsertion() {
        super();
    }
    
    public NewsPaperInsertion(BasicInsertion Insertion, boolean premium,
            long price) {
        super(price);
        
        this.Insertion = Insertion;
        this.premium = premium;
    }
    
    private boolean premium;

    public BasicInsertion getInsertion() {
        return Insertion;
    }

    public void setInsertion(BasicInsertion Insertion) {
        this.Insertion = Insertion;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }
}
