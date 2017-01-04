package de.othr.sw.sharkz.model;

import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.service.InsertionService;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named("insertionPublishment")
public class InsertionPublishmentModel {
    private Insertion insertion;
    private int size;
    
    @Inject
    private InsertionService insertionService;
    
    // GET parameters
    private long insertionId;
    
    public void loadInsertion() {
        insertion = insertionService.getInsertion(insertionId);
        size = insertion.getImages().size();
        insertion.getFurtherAttributes();
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
}
