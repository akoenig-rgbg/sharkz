package de.othr.sw.sharkz.util.conversion;

import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.service.InsertionService;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter("insertionConverter")
public class InsertionConverter implements Converter {

    @Inject InsertionService insertionService;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return insertionService.findInsertion(Long.parseLong(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return String.valueOf(((Insertion) value).getID());
    }
    
    
}
