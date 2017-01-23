package de.othr.sw.sharkz.entity;

import static de.othr.sw.sharkz.entity.Account.HASH_ALGORITHM;
import de.othr.sw.sharkz.entity.util.EntityUtils;
import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class BankingData implements Serializable {
    private String iban;
    private String bic;
    private String bankingPassword;
    private String bankingSalt;
    
    public BankingData() {
        super();
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

    public String getPassword() {
        return bankingPassword;
    }

    public void setPassword(String password) {
        this.bankingSalt = EntityUtils.createRandomString(4);
        try {
            this.bankingPassword = EntityUtils.hashPassword(password, this.bankingSalt, HASH_ALGORITHM);
        } catch (EntityUtils.EntityUtilException ex) {
            throw new RuntimeException("password can not be hashed", ex);
        }
    }

    public String getSalt() {
        return bankingSalt;
    }

    public void setSalt(String salt) {
        this.bankingSalt = salt;
    } 
}
