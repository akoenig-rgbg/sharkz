package de.othr.sw.sharkz.model;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.transaction.Transactional;

@Named(value="home")
@RequestScoped
public class HomeModel {

    public String search() {
        
        return "/search.xhtml?faces-redirect=true&includeViewParams=true";
    }
}
