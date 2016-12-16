package de.othr.sw.sharkz.model;

import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named(value="Home")
@RequestScoped
public class Home {
    
    private Map<String, String> searchMap = new HashMap<>();
    
    public String search() {
        
        FacesContext fc = FacesContext.getCurrentInstance();
        searchMap = fc.getExternalContext().getRequestParameterMap();

        return "search";
    }
}
