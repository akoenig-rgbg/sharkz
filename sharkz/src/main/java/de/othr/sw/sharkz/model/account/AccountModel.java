package de.othr.sw.sharkz.model.account;

import de.othr.sw.sharkz.entity.Account;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SessionScoped
@Named(value="account")
public class AccountModel implements Serializable {
    private Account user;
    private String name;
    
    private boolean isLoggedIn;

    public String logout() {
        isLoggedIn = false;
        user = null;
        name = null;
        
        return "logout";
    }
    
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
}
