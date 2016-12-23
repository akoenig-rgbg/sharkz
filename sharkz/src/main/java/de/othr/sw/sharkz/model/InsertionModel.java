package de.othr.sw.sharkz.model;

import de.othr.sw.sharkz.entity.Address;
import de.othr.sw.sharkz.entity.CommercialAttributes;
import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.entity.InsertionAttributesIF;
import de.othr.sw.sharkz.entity.LivingAttributes;
import de.othr.sw.sharkz.entity.type.HeatingType;
import de.othr.sw.sharkz.entity.type.HouseType;
import de.othr.sw.sharkz.entity.type.OfferType;
import de.othr.sw.sharkz.service.AccountService;
import de.othr.sw.sharkz.service.InsertionService;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named
@ManagedBean
public class InsertionModel implements Serializable {
    
    // Insertion Attributes
    private Insertion insertion;
    private HouseType houseType;
    private OfferType offerType;
    private String description;
    private Address address = new Address();
    private long price;
    private List<File> images;
    private Customer vendor;
    
    // Living Attributes
    private int livingArea;
    private int plotArea;
    private int rooms;
    private int stages;
    private HeatingType heating;
    private boolean guestToilette;
    private boolean basement;
    private boolean kitchen;
    private boolean newBuild;
    private boolean garage;
    private boolean steplessEntry;
    private boolean lift;
    
    // Commercial Attributes
    private int area;
    private boolean aircon;
    private boolean heavyCurrent;
    
    
    @Inject
    AccountModel accountModel;
    
    @Inject
    AccountService accountService;
    
    @Inject
    InsertionService insertionService;
    
    private InsertionAttributesIF attributes;

    public Insertion getInsertion() {
        return insertion;
    }

    public void setInsertion(Insertion insertion) {
        this.insertion = insertion;
    }
    
    public void createInsertion() {
        insertion = new Insertion();
        
        insertion.setAddress(address);
        insertion.setDescription(description);
        insertion.setHouseType(houseType);
        insertion.setImages(images);
        insertion.setOfferType(offerType);
        insertion.setPrice(price);
       
        /*
        long customerID = accountModel.getUser().getID();
        Customer customer = accountService.findCustomer(customerID);
        
        insertion.setVendor(customer);
*/        

        // Living Insertion
        if (houseType.isLiving()) {
            LivingAttributes attr = new LivingAttributes();
            
            attr.setBasement(basement);
            attr.setGarage(garage);
            attr.setGuestToilette(guestToilette);
            attr.setHeating(heating);
            attr.setKitchen(kitchen);
            attr.setLift(lift);
            attr.setLivingArea(livingArea);
            attr.setNewBuild(newBuild);
            attr.setPlotArea(plotArea);
            attr.setRooms(rooms);
            attr.setStages(stages);
            attr.setSteplessEntry(steplessEntry);
            
            attributes = attr;
       
        // Commercial Insertion
        } else {
            CommercialAttributes attr = new CommercialAttributes();
            
            attr.setAircon(aircon);
            attr.setArea(area);
            attr.setHeavyCurrent(heavyCurrent);
            
            attributes = attr;
        }
        
        insertion.setInsertionAttributes(attributes);
        
        insertionService.createInsertion(insertion);
    }
    
    public String test() {
        Insertion in = insertionService.getInsertion(1L);
        
        if (!in.getHouseType().isLiving()) {
            CommercialAttributes attr = (CommercialAttributes) in.getInsertionAttributes();
            
            if (attr.isHeavyCurrent())
                return "success";
        }
        return "failure";
    }

    // Getter & Setter
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

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
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

    public int getLivingArea() {
        return livingArea;
    }

    public void setLivingArea(int livingArea) {
        this.livingArea = livingArea;
    }

    public int getPlotArea() {
        return plotArea;
    }

    public void setPlotArea(int plotArea) {
        this.plotArea = plotArea;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public int getStages() {
        return stages;
    }

    public void setStages(int stages) {
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

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
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
    
    
}
