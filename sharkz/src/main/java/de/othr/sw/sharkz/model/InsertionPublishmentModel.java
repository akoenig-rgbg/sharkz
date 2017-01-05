package de.othr.sw.sharkz.model;

import de.othr.sw.sharkz.entity.CommercialInsertion;
import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.entity.LivingInsertion;
import de.othr.sw.sharkz.entity.type.OfferType;
import de.othr.sw.sharkz.service.InsertionService;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@ViewScoped
@ManagedBean(name="insertionPublishment")
public class InsertionPublishmentModel implements Serializable {
    private Insertion insertion;
    private int size;
    private Map<String, String> importantAttributes;
    
    private int featImgId;
    
    @Inject
    private InsertionService insertionService;
    
    // GET parameters
    private long insertionId;
    
    public void loadInsertion() {
        if (FacesContext.getCurrentInstance().isPostback())
            return;
        
        insertion = insertionService.getInsertion(insertionId);
        importantAttributes = this.fetchImportantAttributes();
        size = insertion.getImages().size();
        
        System.out.println("loadInsertion() wurde aufgerufen");
    }
    
    private Map<String, String> fetchImportantAttributes() {
        Map<String, String> attrs = new LinkedHashMap<>();

        String priceLabel;
        if (insertion.getOfferType() == OfferType.PURCHASE)
            priceLabel = "Kaufpreis";
        else
            priceLabel = "Kaltmiete";
        
        if (insertion.isLivingInsertion()) {
            LivingInsertion ins = (LivingInsertion) insertion;
            
            attrs.put(priceLabel, String.valueOf(insertion.getPrice()) + " &euro;");
            attrs.put("Zi.", String.valueOf(ins.getRooms()));
            attrs.put("Wohnfläche", String.valueOf(ins.getLivingArea()) + " m&sup2;");
            attrs.put("Grundfläche", String.valueOf(ins.getPlotArea()) + " m&sup2;");
        } else {
            CommercialInsertion ins = (CommercialInsertion) insertion;
            
            attrs.put("Fläche", String.valueOf(ins.getArea()));
        }
        
        return attrs;
    }
    
    public String changeFeaturedImage(int id) {
        this.featImgId = id;
        System.out.println("clicked on img with id " + id);
        
        return "";
    }
    
    public String foo(int id) {
        System.out.println("Foo(" + id + ") wurde aufgerufen");
        return "";
    }
    
    // Getter & Setter
    public int getFeatImgId() {
        return this.featImgId;
    }
    
    public void setFeatImgId(int id) {
        this.featImgId = id;
    }
    
    public Map<String, String> getImportantAttributes() {
        return importantAttributes;
    }

    public void setImportantAttributes(Map<String, String> importantAttributes) {
        this.importantAttributes = importantAttributes;
    }
    
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
