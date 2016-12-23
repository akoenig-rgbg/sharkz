package de.othr.sw.sharkz.entity;

import de.othr.sw.sharkz.entity.util.EntityUtils;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class Account extends EntityPrototype {
    public static final String HASH_ALGORITHM = "SHA-512";
    
    @NotNull
    private String eMail;
    @NotNull
    private String password;
    private String salt;
   
    public Account() {
        super();
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.salt = EntityUtils.createRandomString(4);
        try {
            this.password = EntityUtils.hashPassword(password, this.salt, HASH_ALGORITHM);
        } catch (EntityUtils.EntityUtilException ex) {
            throw new RuntimeException("password can not be hashed", ex);
        }
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    
}