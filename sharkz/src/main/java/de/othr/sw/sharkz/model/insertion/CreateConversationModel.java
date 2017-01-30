package de.othr.sw.sharkz.model.insertion;

import de.mu.muckelbauerbank.service.*;
import de.othr.sw.sharkz.entity.Account;
import de.othr.sw.sharkz.entity.Address;
import de.othr.sw.sharkz.entity.Administrator;
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
import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.servlet.http.Part;
import javax.xml.ws.WebServiceRef;

@ConversationScoped
@Named("createCon")
public class CreateConversationModel implements Serializable {

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
    private String loginButtonText = "Login";
    private String registerButtonText = "Registrieren";
    private boolean isLogin = true;
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
    
    // Models & Services
    @Inject private InsertionService insertionService;
    @Inject private AccountModel accountModel;
    @Inject private AccountService accountService;
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
    
    public String next() {
        String outcome = null;
        
        switch(this.step) {
            case 1:
                conversation.begin();
                outcome = createInsertion();
                break;
            case 2:
                outcome = addImages();
                break;
            case 3:
                outcome = publishInsertion();
                conversation.end();
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
    
    public String createInsertion() {
        
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
    

    public void addInsertionAttributes() {
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

    public String login() {
        
        // Validate the form data
        if (!validateData())
            return "";
        
        // Process is a login
        if (isLogin) {
            if (accountService.checkPassword(account.geteMail(), password)) {
                accountModel.setIsLoggedIn(true);
                accountModel.setUser(account);
            } else {
                context.addMessage(null, new FacesMessage(
                    "Falsche E-Mail oder falsches Passwort!"));
                return "";
            }
        }
        
        // Process is a registration
        else if (!isLogin) {
            createCustomer();
            
            accountModel.setIsLoggedIn(true);
            accountModel.setUser(account);
        }
        
        // Set name depending on type of account
        if (account instanceof Customer) {
            accountModel.setName(accountService.getNameByID(
                    account.getID()));
            
            accountModel.init();
            
        } else if (account instanceof Administrator) {
            accountModel.setName("Administrator");
        }
        
        return "upload";
    }
    
    /**
     * Validates if the login data is correct.
     * @return <b>true</b> if data is correct
     *         <b>false</b> otherwise
     */
    public boolean validateData() {
        context = FacesContext.getCurrentInstance();
        
        validateEmail();
        validatePassword();
        
        if (!isLogin)
            validateName();
        
        return context.getMessageList().isEmpty();
    }
    
    private void createCustomer() {
        Customer customer = new Customer();
            
        customer.seteMail(email);
        customer.setPassword(password);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);

        System.out.println("Kunde angelegt");
        System.out.println(customer.getFirstName() + " " + customer.getLastName());

        long customerId = accountService.createCustomer(customer);
        
        account = accountService.findCustomer(customerId);
    }

    private void validateEmail() {
        if (email == null || email.equals("")) {
            context.addMessage(null, new FacesMessage(
                    "Bitte tragen Sie Ihre E-Mail-Addresse ein!"));
        }
        
        // Login: If email does not exist -> account does not exist
        if (isLogin) {
            try {
                account = accountService.getAccountByEmail(email);
            } catch (NoResultException e) {
                context.addMessage(null, new FacesMessage(
                        "Falsche E-Mail oder falsches Passwort!"));
            }
            
        // Registration: If account exists -> choose other email
        } else {
            
                account = accountService.getAccountByEmail(email);
            
            if (account == null) {
                // email can be chosen
                return;
            }
            
            context.addMessage(null, new FacesMessage(
                "Diese E-Mail Addresse ist bereits registriert!"));
        }
    }
    
    private void validatePassword() {
        // No password entered
        if (password == null || password.equals("")) {
            context.addMessage(null, new FacesMessage(
                    "Bitte tragen Sie Ihr Passwort ein!"));
        }

        if (!isLogin) {
            // Other unfullfilled criteria
            if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{5,}$"))
                context.addMessage(null, new FacesMessage(
                        "Ihr Passwort muss aus mindestens 5 Zeichen, "
                                + "Groß- und Kleinbuchstaben und Sonderzeichen "
                                + "(@#$%^&+=) bestehen und darf keinen "
                                + "Whitespace enthalten!"));
        }
    }
    
    private void validateName() {
        if (firstName == null || firstName.equals(""))
            context.addMessage(null, new FacesMessage(
                "Bitte tragen Sie Ihren Vornamen ein!"));
        
        if (lastName == null || lastName.equals(""))
            context.addMessage(null, new FacesMessage(
                "Bitte tragen Sie Ihren Nachnamen ein!"));
    }
    
    /**
     * Toggles between login and registration functionality.
     */
    public void toggleAction() {
        if (isLogin) {
            loginButtonText = "Registrieren";
            registerButtonText = "Einloggen";
        } else {
            loginButtonText = "Login";
            registerButtonText = "Registrieren";
        }
        
        isLogin = !isLogin;
    }
    
    public String addImages() {
        this.addInsertionAttributes();
        
        // Persist insertion as user is definitely logged in now
        insertionId = insertionService.createInsertion(insertion,
                accountModel.getUser().getID());
        
        if (images.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("insertion_form",
                    new FacesMessage("Bitte laden Sie mindestens ein Foto von"
                            + "ihrem Objekt hoch!"));
            
            return null;
        }
        
        insertionService.setInsertionImages(insertionId, images);
        
        return "publish";
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
    
    public String publishInsertion() {
        
        try { // Call Web Service Operation
            TransactionService port = service.getTransactionServicePort();
            
            BankTransaction transaction = new BankTransaction();
            
            transaction.setAmount(3000L);
            
            transaction.setReceiverBIC(Constants.SHARKZ_BIC);
            transaction.setReceiverIBAN(Constants.SHARKZ_IBAN);
            transaction.setReceiverName(Constants.SHARKZ_NAME);
            
            transaction.setSenderBIC(this.bic);
            transaction.setSenderIBAN(this.iban);
            transaction.setSenderName(accountModel.getName());
            transaction.setSenderPassword(this.password);
            
            transaction.setID(12345123L);
            
            boolean result = port.executeTransaction(transaction);
            
            System.out.println("Result = "+result);
        } catch (Exception ex) {
            System.out.println("Banktransaction fehlgeschlagen!");
        }
        
        insertionService.publishInsertion(insertionId, duration,
                publishInNewspaper);
        
        return "insertion";
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

    public String getLoginButtonText() {
        return loginButtonText;
    }

    public void setLoginButtonText(String loginButtonText) {
        this.loginButtonText = loginButtonText;
    }

    public String getRegisterButtonText() {
        return registerButtonText;
    }

    public void setRegisterButtonText(String registerButtonText) {
        this.registerButtonText = registerButtonText;
    }

    public boolean isIsLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
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
    
    public boolean isLoginAction() {
    return isLogin;
    }

    public void setLoginAction(boolean loginAction) {
    this.isLogin = loginAction;
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
}
