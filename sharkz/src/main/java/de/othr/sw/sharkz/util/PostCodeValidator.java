package de.othr.sw.sharkz.util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("postCodeValidator")
public class PostCodeValidator implements Validator {
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String input = (String) value;
        
        //if (input.matches("/^([0]{1}[1-9]{1}|[1-9]{1}[0-9]{1})[0-9]{3}$/"))
        if (input.matches("[0-9]{5}"))
            return;
            
        throw new ValidatorException(new FacesMessage(
                "Bitte geben Sie eine valide Postleitzahl ein!"));
    }
}
