package de.othr.sw.sharkz.model.account;

import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.service.AccountService;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
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
        try {
            if (accountModel.getUser() == null)
                return;

            Customer c = (Customer) accountModel.getUser();
            c = accountService.findCustomer(c.getID());

            iban = c.getBankingData().getIban();
            bic = c.getBankingData().getBic();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void changeBankingData() {
       accountService.updateBankingData(accountModel.getUser().getID(), iban,
                bic, bankingPassword);
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
