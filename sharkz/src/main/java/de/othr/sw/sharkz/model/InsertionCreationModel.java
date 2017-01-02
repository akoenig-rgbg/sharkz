package de.othr.sw.sharkz.model;

import de.othr.sw.sharkz.entity.Address;
import de.othr.sw.sharkz.webservice.CommercialAttributes;
import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.webservice.InsertionAttributesIF;
import de.othr.sw.sharkz.webservice.LivingAttributes;
import de.othr.sw.sharkz.entity.type.HeatingType;
import de.othr.sw.sharkz.entity.type.HouseType;
import de.othr.sw.sharkz.entity.type.OfferType;
import de.othr.sw.sharkz.service.AccountService;
import de.othr.sw.sharkz.service.InsertionService;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named(value="insertionCreation")
public class InsertionCreationModel implements Serializable {
    
    // Insertion Attributes
    private Insertion insertion;
    private HouseType houseType;
    private OfferType offerType;
    private String title;
    private String description;
    private Address address = new Address();
    private String houseNumber;
    private String price;
    private List<File> images;
    private Customer vendor;
    private InsertionAttributesIF attributes;
    
    // Living Attributes
    private String livingArea;
    private String plotArea;
    private String rooms;
    private String stages;
    private HeatingType heating;
    private boolean guestToilette;
    private boolean basement;
    private boolean kitchen;
    private boolean newBuild;
    private boolean garage;
    private boolean steplessEntry;
    private boolean lift;
    
    // Commercial Attributes
    private String area;
    private boolean aircon;
    private boolean heavyCurrent;
    
    // For Ajax events and empty fields
    private boolean livingInsertion = true;
    private String fieldsMissingMessage;
    List<String> emptyFields = new ArrayList<>();
    
    // Models and Services
    @Inject AccountModel accountModel;
    @Inject AccountService accountService;
    @Inject InsertionService insertionService;
    
    // for /publishInsertion.xhtml -> GET parameters
    long insertionId;
    
    public InsertionCreationModel() {
        this.address.setPostCode("");
        this.address.setStreet("");
        this.address.setTown("");
        this.houseNumber = "";
        this.price = "";
    }
    
    public void insertionTypeChanged(AjaxBehaviorEvent event) {
        if (houseType.isLiving()) {
            livingInsertion = true;
        } else {
            livingInsertion = false;
        }
    }
    
    public String createInsertion() {
        // Check if important fields are not filled in
        populateEmptyFields();
        
        if (!emptyFields.isEmpty()) {
            return "";
        }
        
        // Create new insertion and set common attributes
        insertion = new Insertion();
        
        address.setHouseNumber(Integer.parseInt(houseNumber));
        
        insertion.setAddress(address);
        insertion.setDescription(description);
        insertion.setHouseType(houseType);
        insertion.setImages(images);
        insertion.setOfferType(offerType);
        insertion.setPrice(Integer.parseInt(price));
       
        
        long customerID = accountModel.getUser().getID();
        Customer customer = accountService.findCustomer(customerID);
        
        insertion.setVendor(customer);

        // Set insertion-specific attributes
        // Living Insertion
        if (houseType.isLiving()) {
            LivingAttributes attr = new LivingAttributes();
            
            attr.setBasement(basement);
            attr.setGarage(garage);
            attr.setGuestToilette(guestToilette);
            attr.setHeating(heating);
            attr.setKitchen(kitchen);
            attr.setLift(lift);
            attr.setLivingArea(Integer.parseInt(livingArea));
            attr.setNewBuild(newBuild);
            attr.setPlotArea(Integer.parseInt(plotArea));
            attr.setRooms(Integer.parseInt(rooms));
            attr.setStages(Integer.parseInt(stages));
            attr.setSteplessEntry(steplessEntry);
            
            //attributes = attr;
       
        // Commercial Insertion
        } else {
            CommercialAttributes attr = new CommercialAttributes();
            
            attr.setAircon(aircon);
            System.out.println(area);
            attr.setArea(Integer.parseInt(area));
            attr.setHeavyCurrent(heavyCurrent);
            
            attributes = attr;
        }
        
        
        insertionService.createInsertion(insertion);
        
        return "/publishInsertion.xhtml?faces-redirect=true&includeViewParams=true";
    }
    
    private void populateEmptyFields() {
        emptyFields.clear();
        
        System.out.println(
        this.address.getPostCode() +
        this.address.getStreet() +
        this.address.getTown() +
        this.houseNumber +
        this.price);
        
        // Address fields missing
        if (address.getStreet().equals(""))
            emptyFields.add("Straße");
        if (houseNumber.equals(""))
            emptyFields.add("Hausnummer");
        if (address.getPostCode().equals(""))
            emptyFields.add("PLZ");
        if (address.getTown().equals(""))
            emptyFields.add("Ort");
        
        // Price missing
        if (price.equals(""))
            emptyFields.add("Preis");
        
        fieldsMissingMessage = "Bitte füllen Sie folgende Felder aus: ";
        
        if(!emptyFields.isEmpty()) {
            for (String s : emptyFields)
                fieldsMissingMessage += s + ", ";

            StringBuilder b = new StringBuilder(fieldsMissingMessage);
            b.replace(fieldsMissingMessage.lastIndexOf(","),
                    fieldsMissingMessage.lastIndexOf(",") + 1, "!" );
            fieldsMissingMessage = b.toString();
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getter & Setter">

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public long getInsertionId() {
        return insertionId;
    }
    
    public void setInsertionId(long id) {
        this.insertionId = id;
    }
    
    public String getFieldsMissingMessage() {
        return fieldsMissingMessage;
    }
    
    public Insertion getInsertion() {
        return insertion;
    }
    
    public void setInsertion(Insertion insertion) {
        this.insertion = insertion;
    }
    
    public HouseType getHouseType() {
        return houseType;
    }
    
    public void setHouseType(HouseType houseType) {
        this.houseType = houseType;
    }

    public OfferType getOfferType() {
        return offerType;
    }

    public void setOfferType(OfferType offerType) {
        this.offerType = offerType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<File> getImages() {
        return images;
    }

    public void setImages(List<File> images) {
        this.images = images;
    }

    public Customer getVendor() {
        return vendor;
    }

    public void setVendor(Customer vendor) {
        this.vendor = vendor;
    }

    public InsertionAttributesIF getAttributes() {
        return attributes;
    }

    public void setAttributes(InsertionAttributesIF attributes) {
        this.attributes = attributes;
    }

    public String getLivingArea() {
        return livingArea;
    }

    public void setLivingArea(String livingArea) {
        this.livingArea = livingArea;
    }

    public String getPlotArea() {
        return plotArea;
    }

    public void setPlotArea(String plotArea) {
        this.plotArea = plotArea;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getStages() {
        return stages;
    }

    public void setStages(String stages) {
        this.stages = stages;
    }

    public HeatingType getHeating() {
        return heating;
    }

    public void setHeating(HeatingType heating) {
        this.heating = heating;
    }

    public boolean isGuestToilette() {
        return guestToilette;
    }

    public void setGuestToilette(boolean guestToilette) {
        this.guestToilette = guestToilette;
    }

    public boolean isBasement() {
        return basement;
    }

    public void setBasement(boolean basement) {
        this.basement = basement;
    }

    public boolean isKitchen() {
        return kitchen;
    }

    public void setKitchen(boolean kitchen) {
        this.kitchen = kitchen;
    }

    public boolean isNewBuild() {
        return newBuild;
    }

    public void setNewBuild(boolean newBuild) {
        this.newBuild = newBuild;
    }

    public boolean isGarage() {
        return garage;
    }

    public void setGarage(boolean garage) {
        this.garage = garage;
    }

    public boolean isSteplessEntry() {
        return steplessEntry;
    }

    public void setSteplessEntry(boolean steplessEntry) {
        this.steplessEntry = steplessEntry;
    }

    public boolean isLift() {
        return lift;
    }

    public void setLift(boolean lift) {
        this.lift = lift;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public boolean isAircon() {
        return aircon;
    }

    public void setAircon(boolean aircon) {
        this.aircon = aircon;
    }

    public boolean isHeavyCurrent() {
        return heavyCurrent;
    }

    public void setHeavyCurrent(boolean heavyCurrent) {
        this.heavyCurrent = heavyCurrent;
    }

    public AccountModel getAccountModel() {
        return accountModel;
    }

    public void setAccountModel(AccountModel accountModel) {
        this.accountModel = accountModel;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
    
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
    
    public String getHouseNumber() {
        return houseNumber;
    }
    
    public boolean getLivingInsertion() {
        return this.livingInsertion;
    }
    
    public void setLivingInsertion(boolean value) {
        this.livingInsertion = value;
    }
    //</editor-fold>
}
