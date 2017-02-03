package de.othr.sw.sharkz.model.account.admin;

import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.service.InsertionService;
import de.othr.sw.sharkz.service.SearchService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ViewScoped
@Named("insertionMgmt")
public class InsertionManagementModel implements Serializable {
    private List<Insertion> insertions;
    private boolean hasNoResults;
    
    @Inject SearchService searchService;
    @Inject InsertionService insertionService;
    
    @PostConstruct
    public void init() {
        insertions = searchService.fetchAllInsertions();
    }
    
    public void delete(long insertionId) {
        insertionService.deleteInsertion(insertionId);
    }
    
    public void edit(long insertionId) {
        
    }

    public List<Insertion> getInsertions() {
        return insertions;
    }

    public void setInsertions(List<Insertion> insertions) {
        this.insertions = insertions;
    }

    public boolean isHasNoResults() {
        return hasNoResults;
    }

    public void setHasNoResults(boolean hasNoResults) {
        this.hasNoResults = hasNoResults;
    }
    
    
}
