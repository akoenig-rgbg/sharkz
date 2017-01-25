package de.othr.sw.sharkz.model;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@ViewScoped
@Named("message")
public class MessageModel implements Serializable {
    private UIComponent component;

    public void displayMessage(String message) {

        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(component.getClientId(), new FacesMessage(message));
    }
    
    // Getter & Setter
    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }
}
