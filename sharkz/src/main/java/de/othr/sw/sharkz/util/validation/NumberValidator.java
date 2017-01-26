package de.othr.sw.sharkz.util.validation;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("numberValidator")
public class NumberValidator implements Validator {
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String input = (String) value;
        
        String label = (String) component.getAttributes().get("label");
        
        if (input.matches("\\d+") || input.equals(""))
            return;
        
        throw new ValidatorException(new FacesMessage(
                "Bitte geben Sie für " + label + " eine Zahl ein!"));
    }
}
