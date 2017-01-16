package de.othr.sw.sharkz.model.insertion;

import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.service.InsertionService;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named("insertion")
public class InsertionModel {
    private Insertion insertion;
    
    // GET parameters
    private long insertionId;
    
    // Models & Services
    @Inject InsertionService insertionService;
    
    public void loadInsertion() {
        this.insertion = insertionService.getInsertion(insertionId);
    }
    
    // Getter & Setter
    public Insertion getInsertion() {
        return insertion;
    }

    public void setInsertion(Insertion insertion) {
        this.insertion = insertion;
    }

    public long getInsertionId() {
        return insertionId;
    }

    public void setInsertionId(long insertionId) {
        this.insertionId = insertionId;
    }
    
    
}
