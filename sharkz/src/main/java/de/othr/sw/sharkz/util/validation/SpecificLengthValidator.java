package de.othr.sw.sharkz.util.validation;

import de.othr.sw.sharkz.util.Constants;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("specificLengthValidator")
public class SpecificLengthValidator implements Validator {
    
    int num;
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String input = (String) value;
        
        String label = (String) component.getAttributes().get("label");
        
        if (label == null || label.equals("")) {
            label = "ERROR_NO_LABEL";
            return;
        }
        
        switch (label) {
            case "Titel":
                num = Constants.INSERTION_TITLE_LENGTH;
                break;
            case "Beschreibung":
                num = Constants.INSERTION_DESCRIPTION_LENGTH;
                break;
        }
            
        if (input.length() > num)
            throw new ValidatorException(new FacesMessage(
                    "Bitte geben Sie höchstens " + num + " Zeichen im Feld "
                            + label + " ein!"));
    }
}
