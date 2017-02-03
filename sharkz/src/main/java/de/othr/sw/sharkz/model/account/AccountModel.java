package de.othr.sw.sharkz.model.account;

import de.othr.sw.sharkz.entity.Account;
import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.service.AccountService;
import de.othr.sw.sharkz.service.InsertionService;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SessionScoped
@Named(value="account")
public class AccountModel implements Serializable {
    private Account user;
    private String name;
    
    // Form attributes
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    
    private String oldPassword;
    private String newPassword;
    
    private boolean isLoggedIn;
    
    // Models & Services
    @Inject private AccountService accountService;
    @Inject private InsertionService insertionService;
    @Inject private Logger logger;

    public void init() {
        if (user instanceof Customer) {
            Customer c = (Customer) user;
            
            firstName = c.getFirstName();
            lastName = c.getLastName();
            email = c.geteMail();
            phoneNumber = c.getPhoneNumber();
        }
    }
    
    /**
     * Updates the data of a customer
     */
    public void changeData() {
        accountService.updateCustomerData(user.getID(), firstName, lastName,
                phoneNumber, email);
    }
    
    /**
     * Changes the password of a customer
     */
    public void changePassword() {
        if (accountService.checkPassword(user.geteMail(), oldPassword)) {
            accountService.updatePassword(user.getID(), newPassword);
        }
    }
    
    /**
     * Logs out the current user
     * @return 
     */
    public String logout() {
        logger.info("User (" + user.getID() + ") logged out!");
        
        isLoggedIn = false;
        user = null;
        name = null;
        
        return "logout";
    }
    
    /**
     * Retrieves the number of registered customers
     * @return 
     */
    public String getNumOfCustomers() {
        return accountService.getNumOfCustomers();
    }
    
    /**
     * Retrieves the number of all published insertions
     * @return 
     */
    public String getNumOfInsertions() {
        return insertionService.getNumOfInsertions();
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getter & Setter">
    public Account getUser() {
        return user;
    }
    
    public void setUser(Account acc) {
        this.user = acc;
    }
    
    public boolean isIsLoggedIn() {
        return isLoggedIn;
    }
    
    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
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
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getOldPassword() {
        return oldPassword;
    }
    
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    
    public String getNewPassword() {
        return newPassword;
    }
    
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
//</editor-fold>
}
