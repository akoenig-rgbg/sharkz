package de.othr.sw.sharkz.model.insertion;

import de.mu.muckelbauerbank.service.*;
import de.othr.sw.newspaper.service.*;
import de.othr.sw.sharkz.entity.Account;
import de.othr.sw.sharkz.entity.Address;
import de.othr.sw.sharkz.entity.BankingData;
import de.othr.sw.sharkz.entity.CommercialInsertion;
import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.entity.LivingInsertion;
import de.othr.sw.sharkz.entity.type.HeatingType;
import de.othr.sw.sharkz.entity.type.HouseType;
import de.othr.sw.sharkz.entity.type.OfferType;
import de.othr.sw.sharkz.model.account.AccountModel;
import de.othr.sw.sharkz.service.AccountService;
import de.othr.sw.sharkz.service.InsertionService;
import de.othr.sw.sharkz.util.Constants;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.xml.ws.WebServiceRef;

@ConversationScoped
@Named("createCon")
public class CreateConversationModel implements Serializable {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/im-lamport_8080/Newspaper/NewspaperTransactionService.wsdl")
    private NewspaperTransactionServiceService service_1;

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/im-lamport_8080/muckelbauerBank/TransactionService.wsdl")
    private TransactionServiceService service;
    
    
    //<editor-fold defaultstate="collapsed" desc="Attributes">
// Conversation related
    @Inject Conversation conversation;
    private int step;
    
    // Insertion
    private Insertion insertion;
    private long insertionId;
    
    // Insertion Attributes
    private HouseType houseType;
    private OfferType offerType;
    private String title;
    private String description;
    private Address address = new Address();
    private String houseNumber;
    private String price;
    private Customer vendor;
    
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
    
    // Image Upload
    private Part file;
    private List<String> fileNames;
    private List<byte[]> images;
    
    // For Ajax events
    private boolean livingInsertion;
    private int featImgId;
    
    // Login
    private Account account;
    
    // Form inputs
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    
    // For messages
    FacesContext context;
    
    // Publishment
    private int duration;
    private String iban;
    private String bic;
    private String bankingPassword;
    private boolean publishInNewspaper;
    private UIComponent publishButton;
    
    // Models & Services
    @Inject private InsertionService insertionService;
    @Inject private AccountModel accountModel;
    @Inject private AccountService accountService;
    @Inject private Logger logger;
    
//</editor-fold>
    
    @PostConstruct
    public void init() {
        this.step = 1;
        
        this.insertion = new LivingInsertion();
        this.livingInsertion = true;
        this.address.setPostCode("");
        this.address.setStreet("");
        this.address.setTown("");
        this.houseNumber = "";
        this.price = "";
        
        fileNames = new ArrayList<>();
        images = new ArrayList<>();
        
    }
    
    /**
     * Proceed to the next step of the insertion creation.
     * @return the outcome
     */
    public String next() {
        String outcome = null;
        
        switch(this.step) {
            case 1:
                if (this.conversation.isTransient())
                    conversation.begin();
                outcome = createInsertion();
                break;
            case 2:
                outcome = addImages();
                break;
            case 3:
                outcome = publishInsertion();
                break;
        }
        
        System.out.println("Step: " + step + ", outcome: " + outcome);
        
        if (outcome != null && !outcome.equals(""))
            step++;
        
        return outcome;
    }
    
    /**
     * Get the CSS class
     * @param step the number of the step
     * @return "active" if step current or already done
     *         "passive" else
     */
    public String getStepClass(int step) {
        if (this.step >= step)
            return "active";
        
        return "passive";
    }
    
    /**
     * Create a new insertion based on the input on create.xhtml
     * @return the outcome (logon if required, upload else)
     */
    private String createInsertion() {
        
        if (houseType.isLiving()) {
            livingInsertion = true;
       
        } else {
            livingInsertion = false;
        }
        
        // If not logged in -> forward to login page
        if (!accountModel.isIsLoggedIn()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    "Bitte loggen Sie sich ein oder legen Sie einen Account an"
                            + " bevor Sie ein Inserat anlegen!"));
            
            return "logon";
        } else {
            // Forward to publishment page
            return "upload";
        }
    }

    /**
     * Set the attributes of the new insertion.
     */
    private void addInsertionAttributes() {
        if (livingInsertion) {
            LivingInsertion ins = new LivingInsertion();

            ins.setBasement(basement);
            ins.setGarage(garage);
            ins.setGuestToilette(guestToilette);
            ins.setHeating(heating);
            ins.setKitchen(kitchen);
            ins.setLift(lift);
            ins.setLivingArea(Integer.parseInt(livingArea));
            ins.setNewBuild(newBuild);
            ins.setPlotArea(Integer.parseInt(plotArea));
            ins.setRooms(Integer.parseInt(rooms));
            ins.setStages(Integer.parseInt(stages));
            ins.setSteplessEntry(steplessEntry);
            
            insertion = ins;
        } else {
            CommercialInsertion ins = new CommercialInsertion();
            
            ins.setAircon(aircon);
            ins.setArea(Integer.parseInt(area));
            ins.setHeavyCurrent(heavyCurrent);
            
            insertion = ins;
        }
        
        // Set common attributes
        address.setHouseNumber(Integer.parseInt(houseNumber));
        
        insertion.setAddress(address);
        insertion.setTitle(title);
        insertion.setDescription(description);
        insertion.setHouseType(houseType);
        insertion.setOfferType(offerType);
        insertion.setPrice(Integer.parseInt(price));   
    }
    
    /**
     * Add the uploaded images to the insertion and persist them.
     * @return the outcome
     */
    private String addImages() {
        if (images.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("insertion_form",
                    new FacesMessage("Bitte laden Sie mindestens ein Foto von "
                            + "Ihrer Immobilie hoch!"));
            
            return null;
        }
        
        this.addInsertionAttributes();
        
        // Persist insertion as user is definitely logged in now
        insertionId = insertionService.createInsertion(insertion,
                accountModel.getUser().getID());
        
        // Load BankingData if available
        loadBankingData();
        
        logger.info("User with ID " + accountModel.getUser().getID()
                + " created a new Insertion!");
        
        insertionService.setInsertionImages(insertionId, images);
        
        return "publish";
    }
    
    private void loadBankingData() {
        BankingData data = ((Customer) accountModel.getUser()).getBankingData();
        
        if (data != null) {
            this.iban = data.getIban();
            this.bic = data.getBic();
        }
    }
    
    /**
     * Uploads an image to the model and saves it as <code>byte[]</code>
     */
    public void uploadImage() {
        fileNames.add(file.getSubmittedFileName());
        
        try (InputStream input = file.getInputStream()) {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[Constants.MAX_IMAGE_SIZE];
            
            for (int length = 0; (length = input.read(buffer)) > 0;)
                output.write(buffer, 0, length);

            images.add(output.toByteArray());

        } catch (IOException e) {
            
        }
    }
    
    /**
     * Validates the files, the user wants to upload as an image for the
     * insertion.
     */
    public void validateFile(FacesContext ctx, UIComponent comp, Object value) {
        Part file = (Part) value;
        List<FacesMessage> msgs = new ArrayList<>();
        
        // Too many pictures
        if (images.size() > 4) {
            msgs.add(new FacesMessage("Sie können nur 5 Bilder hochladen"));
            throw new ValidatorException(msgs);
        }
        
        // Picture size > 3MB
        if (file.getSize() > Constants.MAX_IMAGE_SIZE) {
            msgs.add(new FacesMessage("Die maximale Dateigröße beträgt 3MB!"));
        }
        
        String type = file.getContentType();
        
        // File is no picture
        if (!type.equalsIgnoreCase("image/png")
                && !(type.equalsIgnoreCase("image/jpeg"))) {
            msgs.add(new FacesMessage("Bitte laden Sie nur .jpeg oder .png "
                    + "Dateien hoch!"));
        }
        
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }

    }
    
    /**
     * Execute a bank transaction and publish the created insertion.
     * @return the outcome of the insertion page
     */
    private String publishInsertion() {
        
        // Check if all needed input is available
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        if (iban == null || iban.equals(""))
            ctx.addMessage(publishButton.getClientId(), new FacesMessage(
                    "Bitte geben Sie Ihre IBAN ein!"));
        
        if (bic == null || bic.equals(""))
            ctx.addMessage(publishButton.getClientId(), new FacesMessage(
                    "Bitte geben Sie Ihre BIC ein!"));
        
        if (bankingPassword == null || bankingPassword.equals(""))
            ctx.addMessage(publishButton.getClientId(), new FacesMessage(
                    "Bitte geben Sie Ihr Bankpasswort ein!"));
        
        if (ctx.getMessageList().size() > 0)
            return null;

        // Do the bank transaction
        try {
            if (!doBankTransaction()) {
                ctx.addMessage(publishButton.getClientId(), new FacesMessage(
                        "Die Banktransaktion ist fehlgeschlagen! Bitte überprüfen "
                                + "Sie Ihre Angaben!"));

                return null;
            }
        } catch (Exception e) {
            ctx.addMessage(publishButton.getClientId(), new FacesMessage(
                    "Die Banktransaktion ist fehlgeschlagen! Der Service ist"
                            + " im Moment nicht erreichbar!"));
            
            return null;
        }
        
        if (publishInNewspaper) {
            
            try {
                if (!doNewspaperTransaction()) {
                    ctx.addMessage(publishButton.getClientId(), new FacesMessage(
                            "Die Veröffentlichung in der Zeitung ist "
                                    + "fehlgeschlagen! Ihr Inserat kann nur auf "
                                    + "Sharkz veröffentlicht werden!"));
                
                    return null;
                }
            } catch (Exception e) {
                ctx.addMessage(publishButton.getClientId(), new FacesMessage(
                    "Der Service zum Veröffentlichen in der Zeitung steht im "
                            + "Moment nicht zur Verfügung! Ihr Inserat kann "
                            + "nur auf Sharkz veröffentlicht werden!"));
            
                return null;
            }
        }
        
        // Publish insertion => persist Order
        insertionService.publishInsertion(insertionId, duration,
                publishInNewspaper);
        
        logger.info("User with ID " + accountModel.getUser().getID()
                + " published insertion (" + insertionId + ")!");
        
        
        // No errors until here => end of conversation
        if (!this.conversation.isTransient())
            conversation.end();
        
        return "insertion.xhtml?faces-redirect=true&includeViewParams=true&insertion_id=" + insertionId;
    }
    
    /**
     * Transfers money from the customer's bank account to Sharkz as payment
     * for the publishment of the insertion.
     * @return true if everything worked
     * false otherwise
     * @throws Exception if BankTransactionService is not available 
     */
    private boolean doBankTransaction() throws Exception {
            TransactionService port = service.getTransactionServicePort();
            
            BankTransaction transaction = new BankTransaction();
            
            long cost;
            
            if (duration == 1)
                cost = 1500;
            else if (duration == 3)
                cost = 4000;
            else
                cost = 7500;
           
            transaction.setAmount(cost);
            
            transaction.setPurpose("Publishment of insertion \"" 
                    + insertion.getTitle() + "\" for " + duration + " months.");
            
            transaction.setReceiverBIC(Constants.SHARKZ_BIC);
            transaction.setReceiverIBAN(Constants.SHARKZ_IBAN);
            transaction.setReceiverName(Constants.SHARKZ_NAME);
            
            transaction.setSenderBIC(this.bic);
            transaction.setSenderIBAN(this.iban);
            transaction.setSenderName(accountModel.getName());
            transaction.setSenderPassword(this.password);
            
            transaction.setID(12345123L);
            
            return port.executeTransaction(transaction);
    }
    
    /**
     * Send the insertion to publish to the newspaper
     * @return true if everything worked
     * false otherwise
     * @throws Exception if NewspaperTransactionService is not available
     */
    private boolean doNewspaperTransaction() throws Exception {
            NewspaperTransactionService port = 
                    service_1.getNewspaperTransactionServicePort();
            
            NewspaperTransaction transaction = new NewspaperTransaction();
            
            Address adr = insertion.getAddress();
            
            de.othr.sw.newspaper.service.Address address = 
                    new de.othr.sw.newspaper.service.Address();
            
            address.setAdditionalLetter(adr.getAdditionalLetter());
            address.setHouseNumber(adr.getHouseNumber());
            address.setPostCode(adr.getPostCode());
            address.setStreet(adr.getStreet());
            address.setTown(adr.getTown());
            
            transaction.setAddress(address);
            transaction.setDescription(insertion.getTitle());
            transaction.setHouseType(insertion.getHouseType().getLabel());
            transaction.setLink("im-lamport:8080/Sharkz/insertion/insertion"
                    + ".xhtml?insertion_id=" + insertionId);
            transaction.setOfferType(insertion.getOfferType().getLabel());
            transaction.setPrice(insertion.getPrice());
            transaction.setTitle(insertion.getTitle());
            
            return port.sendNewspaperInsertion(transaction);
    }

    //<editor-fold defaultstate="collapsed" desc="Getter & Setter">

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
    
    public long getInsertionId() {
        return insertionId;
    }
    
    public void setInsertionId(long insertionId) {
        this.insertionId = insertionId;
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
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
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
    
    public String getHouseNumber() {
        return houseNumber;
    }
    
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
    
    public String getPrice() {
        return price;
    }
    
    public void setPrice(String price) {
        this.price = price;
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
    
    public Part getFile() {
        return file;
    }
    
    public void setFile(Part file) {
        this.file = file;
    }
    
    public List<String> getFileNames() {
        return fileNames;
    }
    
    public void setFileNames(List<String> fileNames) {
        this.fileNames = fileNames;
    }
    
    public List<byte[]> getImages() {
        return images;
    }
    
    public void setImages(List<byte[]> images) {
        this.images = images;
    }
    
    public boolean isLivingInsertion() {
        return livingInsertion;
    }
    
    public void setLivingInsertion(boolean livingInsertion) {
        this.livingInsertion = livingInsertion;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public int getFeatImgId() {
        return featImgId;
    }
    
    public void setFeatImgId(int featImgId) {
        this.featImgId = featImgId;
    }
    
    public int getDuration() {
        return duration;
    }
    
    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    public String getIban() {
        return iban;
    }
    
    public void setIban(String iban) {
        this.iban = iban;
    }
    
    public String getBic() {
        return bic;
    }
    
    public void setBic(String bic) {
        this.bic = bic;
    }
    
    public String getBankingPassword() {
        return bankingPassword;
    }
    
    public void setBankingPassword(String bankingPassword) {
        this.bankingPassword = bankingPassword;
    }
    
    public boolean isPublishInNewspaper() {
        return publishInNewspaper;
    }
    
    public void setPublishInNewspaper(boolean publishInNewspaper) {
        this.publishInNewspaper = publishInNewspaper;
    }
    
    public UIComponent getPublishButton() {
        return publishButton;
    }
    
    public void setPublishButton(UIComponent publishButton) {
        this.publishButton = publishButton;
    }
//</editor-fold>
    
    
}
