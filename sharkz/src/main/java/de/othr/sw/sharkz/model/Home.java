package de.othr.sw.sharkz.model;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.transaction.Transactional;

@Named(value="home")
@RequestScoped
public class Home {

    @Transactional(Transactional.TxType.REQUIRED)
    public String search() {
        
        return "search?includeViewParams=true&faces-redirect=true";
    }
}
