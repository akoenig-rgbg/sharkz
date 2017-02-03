package de.othr.sw.sharkz.util.conversion;

import de.othr.sw.sharkz.entity.Account;
import de.othr.sw.sharkz.entity.Customer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("accountConverter")
public class AccountConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        // Not needed
        
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null)
            return "";
        
        if (!(value instanceof Account || value instanceof Customer))
            return "";
        
        if (value instanceof Customer) {
            Customer c = (Customer) value;

            return c.getFirstName() + " " + c.getLastName();
        }
        
        return "Administrator";
    }
    
}
