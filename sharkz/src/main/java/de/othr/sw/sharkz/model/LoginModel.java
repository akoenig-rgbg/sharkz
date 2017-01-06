package de.othr.sw.sharkz.model;

import de.othr.sw.sharkz.entity.Account;
import de.othr.sw.sharkz.entity.Administrator;
import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.service.AccountService;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

@ViewScoped
@Named
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
                
                // Set name depending on type of account
                if (acc instanceof Customer) {
                    accountModel.setName(accountService.getNameByID(acc.getID()));
                } else if (acc instanceof Administrator) {
                    accountModel.setName("Administrator");
                }
                
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
    
    public void validatePassword(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        
        if (isLogin)
            return;
        
        String input = value.toString();

        String label = (String) component.getAttributes().get("label");
        
        if (label == null)
            label = "ERROR_NO_LABEL";
        else
        
        if (input == null || input.equals(""))
            throw new ValidatorException(new FacesMessage(
                    "Bitte tragen Sie einen Wert für " + label + " ein!"));
        
        if (!input.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{5,}$"))
            throw new ValidatorException(new FacesMessage(
                    "Ihr Passwort aus mindestens 5 Zeichen bestehen und Groß- und Kleinbuchstaben, Sonderzeichen (@#$%^&+=) sowie keinen Whitespace enthalten!"));
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
