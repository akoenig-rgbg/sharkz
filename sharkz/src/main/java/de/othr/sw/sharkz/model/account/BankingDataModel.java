package de.othr.sw.sharkz.model.account;

import de.othr.sw.sharkz.entity.BankingData;
import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.service.AccountService;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ViewScoped
@Named("bankingData")
public class BankingDataModel implements Serializable {
    
    // Form attributes
    private String iban;
    private String bic;
    private String bankingPassword;
    
    // Models & Services
    @Inject private AccountModel accountModel;
    @Inject private AccountService accountService;
    
    @PostConstruct
    public void init() {
        if (accountModel.getUser() == null)
            return;

        Customer c = (Customer) accountModel.getUser();
        c = accountService.findCustomer(c.getID());
        
        BankingData b = c.getBankingData();
        
        if (b != null) {
            iban = c.getBankingData().getIban();
            bic = c.getBankingData().getBic();
        }

    }
    
    /**
     * Updates the BankingData of a customer
     */
    public void changeBankingData() {
       accountService.updateBankingData(accountModel.getUser().getID(), iban,
                bic, bankingPassword);
    }
    
    // Getter & Setter
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
