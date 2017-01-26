package de.othr.sw.sharkz.model.account;

import de.othr.sw.sharkz.entity.BankingData;
import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.service.AccountService;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named("bankingData")
public class BankingDataModel {
    
    // Form attributes
    private String iban;
    private String bic;
    private String bankingPassword;
    
    // Models & Services
    @Inject private AccountService accountService;
    @Inject private AccountModel accountModel;
    
    @PostConstruct
    public void init() {
        Customer c = (Customer) accountModel.getUser();
        
        iban = c.getBankingData().getIban();
        bic = c.getBankingData().getBic();
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
