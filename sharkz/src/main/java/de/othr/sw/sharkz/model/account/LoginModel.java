package de.othr.sw.sharkz.model.account;

import de.othr.sw.sharkz.entity.Account;
import de.othr.sw.sharkz.entity.Administrator;
import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.model.insertion.PublishModel;
import de.othr.sw.sharkz.service.AccountService;
import de.othr.sw.sharkz.service.InsertionService;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

@ViewScoped
@Named("login")
public class LoginModel implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Attributes">
    
    Account account;
    
    // Form inputs
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    
    // Register/Login toggle fields
    private String loginButtonText = "Login";
    private String registerButtonText = "Registrieren";
    private boolean isLogin = true;
    
    // Login validation
    FacesContext context;
    
    // If login is required from insertion/create.xhtml
    Insertion insertion;
    
    // Models & Services
    @Inject InsertionService insertionService;
    @Inject AccountService accountService;
    @Inject @Named("account") AccountModel accountModel;
    @Inject PublishModel publishModel;

    //</editor-fold>

    @PostConstruct
    public void getInsertion() {
        insertion = (Insertion) FacesContext.getCurrentInstance()
                .getExternalContext().getFlash().get("insertion");
    }
    
    /**
     * Login to the sharkz website.
     * @return 
     */
    public String login() {
        
        // Validate the form data
        if (!validateData())
            return "";
        
        // Process is a login
        if (isLogin) {
            if (accountService.checkPassword(account.geteMail(), password)) {
                accountModel.setIsLoggedIn(true);
                accountModel.setUser(account);
            }
            else {
                context.addMessage(null, new FacesMessage(
                    "Falsche E-Mail oder falsches Passwort!"));
                return "";
            }
        }
        
        // Process is a registration
        else if (!isLogin) {
            createCustomer();
            
            accountModel.setIsLoggedIn(true);
            accountModel.setUser(account);
        }
        
        // Set name depending on type of account
        if (account instanceof Customer) {
            accountModel.setName(accountService.getNameByID(
                    account.getID()));
            
            accountModel.init();
            
        } else if (account instanceof Administrator) {
            accountModel.setName("Administrator");
        }
        
        // Normal login
        if (insertion == null)
            return "index";
        
        // Login is required for insertion creation
        insertion.setVendor((Customer) accountModel.getUser());
        publishModel.setInsertionId(insertionService.createInsertion(insertion));
        
        return "publish";
    }
    
    /**
     * Validates if the login data is correct.
     * @return <b>true</b> if data is correct
     *         <b>false</b> otherwise
     */
    public boolean validateData() {
        context = FacesContext.getCurrentInstance();
        
        validateEmail();
        validatePassword();
        
        if (!isLogin)
            validateName();
        
        return context.getMessageList().isEmpty();
    }
    
    private void createCustomer() {
        Customer customer = new Customer();
            
        customer.seteMail(email);
        customer.setPassword(password);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);

        System.out.println("Kunde angelegt");
        System.out.println(customer.getFirstName() + " " + customer.getLastName());

        accountService.createCustomer(customer);
    }

    private void validateEmail() {
        if (email == null || email.equals("")) {
            context.addMessage(null, new FacesMessage(
                    "Bitte tragen Sie Ihre E-Mail-Addresse ein!"));
        }
        
        // Login: If email does not exist -> account does not exist
        if (isLogin) {
            try {
                account = accountService.getAccountByEmail(email);
            } catch (NoResultException e) {
                context.addMessage(null, new FacesMessage(
                        "Falsche E-Mail oder falsches Passwort!"));
            }
            
        // Registration: If account exists -> choose other email
        } else {
            try {
                account = accountService.getAccountByEmail(email);
            } catch (NoResultException e) {
                // email can be chosen
            }
            
            context.addMessage(null, new FacesMessage(
                "Diese E-Mail Addresse ist bereits registriert!"));
        }
    }
    
    private void validatePassword() {
        // No password entered
        if (password == null || password.equals("")) {
            context.addMessage(null, new FacesMessage(
                    "Bitte tragen Sie Ihr Passwort ein!"));
        }

        if (!isLogin) {
            // Other unfullfilled criteria
            if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{5,}$"))
                context.addMessage(null, new FacesMessage(
                        "Ihr Passwort aus mindestens 5 Zeichen bestehen und "
                                + "Groß- und Kleinbuchstaben, Sonderzeichen "
                                + "(@#$%^&+=) sowie keinen Whitespace enthalten!"));
        }
    }
    
    private void validateName() {
        if (firstName == null || firstName.equals(""))
            context.addMessage(null, new FacesMessage(
                "Bitte tragen Sie Ihren Vornamen ein!"));
        
        if (lastName == null || lastName.equals(""))
            context.addMessage(null, new FacesMessage(
                "Bitte tragen Sie Ihren Nachnamen ein!"));
    }
    
    /**
     * Toggles between login and registration functionality.
     */
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
    
    //<editor-fold defaultstate="collapsed" desc="Getter & Setter">
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
    //</editor-fold>
}
