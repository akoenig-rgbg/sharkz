package de.othr.sw.sharkz.model.insertion;

import de.othr.sw.sharkz.model.account.AccountModel;
import de.othr.sw.sharkz.entity.CommercialInsertion;
import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.entity.LivingInsertion;
import de.othr.sw.sharkz.entity.type.OfferType;
import de.othr.sw.sharkz.service.InsertionService;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

@ViewScoped
@Named(value="publish")
public class PublishModel implements Serializable {
    private Insertion insertion;
    private int size;
    private Map<String, String> importantAttributes;
    
    private int duration = 1;
    
    private int featImgId;
    
    private boolean successfulValidation = false;
    
    // Services & Models
    @Inject private InsertionService insertionService;
    @Inject private AccountModel accountModel;
    @Inject private InsertionModel insertionModel;
    
    // GET parameters
    private long insertionId;
    
    public PublishModel() {
        super();
    }

    public void loadInsertion() {
        if (FacesContext.getCurrentInstance().isPostback())
            return;
        
        insertion = insertionService.getInsertion(insertionId);
        importantAttributes = this.fetchImportantAttributes();
        size = insertion.getImages().size();
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
            
            
            String price = NumberFormat.getNumberInstance(
                    Locale.GERMAN).format(insertion.getPrice());
            
            attrs.put(priceLabel, price + " &euro;");
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
        
        return "";
    }
    
    public void validatePublishment(FacesContext ctx, UIComponent comp,
            Object value) {
        
        try {
        System.out.println("accountModel: " + accountModel.getUser().getID());
        
        System.out.println("insertion: " + insertion.getVendor().getID());
        } catch (NullPointerException e) {
            System.out.println("Nullpointerexception beim Vendor/User");
        }
        
        List<FacesMessage> msgs = new ArrayList<>();
        
        // validate input
        if (duration <= 0)
            msgs.add(new FacesMessage("Die Dauer der "
                    + "Veröffentlichung muss <= 0 sein!"));
        
        if (insertion == null)
            msgs.add(new FacesMessage("Das Inserat mit ID " + insertionId
                    + "existiert nicht!"));
        
        if (!accountModel.isIsLoggedIn())
            msgs.add(new FacesMessage("Sie müssen eingeloggt sein, um das "
                    + "Inserat zu veröffentlichen!"));
        
        else if (!accountModel.getUser().equals(insertion.getVendor()))
            msgs.add(new FacesMessage("Sie können nur Ihre eigenen Inserate "
                    + "veröffentlichen!"));
        
        if (!msgs.isEmpty())
            throw new ValidatorException(msgs);
        
        else
            successfulValidation = true;
    }
    
    public String publishInsertion() {
        // publish insertion
        insertionService.publishInsertion(insertion, duration);
       
        // Propagate GET parameters
        insertionModel.setInsertionId(insertionId);
        
        // redirect
        if (successfulValidation)
            return "success";
        
        return "failure";
    }
    
    // Getter & Setter
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    
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
