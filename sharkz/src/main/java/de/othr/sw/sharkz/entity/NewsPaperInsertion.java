package de.othr.sw.sharkz.entity;

import javax.persistence.Entity;

@Entity
public class NewsPaperInsertion extends Product {
    private BasicInsertion Insertion;
    
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
