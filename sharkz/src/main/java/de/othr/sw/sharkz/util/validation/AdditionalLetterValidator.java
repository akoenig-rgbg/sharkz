package de.othr.sw.sharkz.util.validation;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("additionalLetterValidator")
public class AdditionalLetterValidator implements Validator {
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null)
            return;
        
        char input = (char) value;
        
        String label = (String) component.getAttributes().get("label");
        
        if ((input >= 65 && input <= 90 ) || (input >= 97 && input <= 122))
            return;
            
        throw new ValidatorException(new FacesMessage(
                "Bitte geben Sie für " + label + " einen einzelnen Buchstaben ein!"));
    }
}
