package de.othr.sw.sharkz.util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value="requiredValidator")
public class RequiredValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        
        String input = (String) value;
        
        String label = (String) component.getAttributes().get("label");
        
        if (label == null)
            label = "ERROR_NO_LABEL";
        else
        
        if (input == null || input.equals(""))
            throw new ValidatorException(new FacesMessage(
                    "Bitte tragen Sie einen Wert für " + label + " ein!"));
    }
}
