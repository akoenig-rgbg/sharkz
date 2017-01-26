package de.othr.sw.sharkz.util.conversion;

import de.othr.sw.sharkz.entity.Address;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("addressConverter")
public class AddressConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null)
            return null;
        
        if (!value.getClass().equals(Address.class))
            return null;
        
        Address adr = (Address) value;
        
        String s = adr.getPostCode() + " "
                + adr.getTown() + ", "
                + adr.getStreet() + " " + adr.getHouseNumber();
        
        if (!(adr.getAdditionalLetter() == '\u0000'))
            s += adr.getAdditionalLetter();
        
        return s;
    }
    
}
