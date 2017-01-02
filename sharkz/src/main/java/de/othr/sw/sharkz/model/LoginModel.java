package de.othr.sw.sharkz.model;

import de.othr.sw.sharkz.entity.Account;
import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.service.AccountService;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

@ViewScoped
@ManagedBean
public class LoginModel implements Serializable {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    
    private String loginButtonText = "Login";
    private String registerButtonText = "Registrieren";
    
    private boolean isLogin = true;
    
    @Inject
    AccountService accountService;
    
    @Inject
    AccountModel accountModel;

    public String loginButtonPress() {
        
        // Login
        if (isLogin) {
            
            Account acc = accountService.getAccountByEmail(email);
            
            if (accountService.checkPassword(acc.geteMail(), password)) {
                
                accountModel.setIsLoggedIn(true);
                
                accountModel.setUser(acc);
                accountModel.setName(accountService.getNameByID(acc.getID()));
                
                return "login_successful";
            }
            else {
                return "";
            }
        }
        
        // Register
        else if (!isLogin) {
            Customer customer = new Customer();
            
            customer.seteMail(email);
            customer.setPassword(password);
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            
            System.out.println("Kunde angelegt");
            System.out.println(customer.getFirstName() + " " + customer.getLastName());
            
            accountService.createCustomer(customer);
        }
        
        return "index?faces-redirect=true";
    }
    
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

    public boolean isLoginAction() {
        return isLogin;
    }

    public void setLoginAction(boolean loginAction) {
        this.isLogin = loginAction;
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

    public boolean isIsLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }
    
    
}
