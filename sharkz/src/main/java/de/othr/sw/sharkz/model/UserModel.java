package de.othr.sw.sharkz.model;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value="login")
@SessionScoped
public class UserModel implements Serializable {
    private String email;
    private String password;
    
    private String loginButtonText = "Login";
    private String registerButtonText = "Registrieren";
    
    private boolean isLogin = true;

    public String loginButtonPress() {
        return "index";
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
        
        System.out.println("Aufruf");
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
    
    
}
