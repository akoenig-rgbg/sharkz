package de.othr.sw.sharkz.model;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@RequestScoped
@Named(value="home")
public class IndexModel {

    public String search() {
        return "search";
    }
}
