package de.othr.sw.sharkz.model.account;

import de.othr.sw.sharkz.entity.Account;
import de.othr.sw.sharkz.entity.BankingData;
import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.service.AccountService;
import java.io.Serializable;
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
    
    private String iban;
    private String bic;
    private String bankingPassword;
    
    private boolean isLoggedIn;
    
    // Models & Services
    @Inject private AccountService accountService;

    public void init() {
        if (user instanceof Customer) {
            Customer c = (Customer) user;
            
            firstName = c.getFirstName();
            lastName = c.getLastName();
            email = c.geteMail();
            phoneNumber = c.getPhoneNumber();
        }
    }
    
    public void changeData() {
        Customer c = (Customer) user;
        
        c.setFirstName(firstName);
        c.setLastName(lastName);
        c.setPhoneNumber(phoneNumber);
        c.seteMail(email);
        
        accountService.updateAccount(c);
    }
    
    public void changePassword() {
        if (accountService.checkPassword(user.geteMail(), oldPassword)) {
            user.setPassword(newPassword);
            accountService.updateAccount(user);
        }
    }
    
    public void changeBankingData() {
        BankingData bankingData = new BankingData();
        
        bankingData.setIban(iban);
        bankingData.setBic(bic);
        bankingData.setPassword(oldPassword);
        
        Customer c = (Customer) user;
        
        c.setBankingData(bankingData);
        accountService.updateAccount(c);
    }
    
    public String logout() {
        isLoggedIn = false;
        user = null;
        name = null;
        
        return "logout";
    }
    
    // Getter & Setter
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
}
