package de.othr.sw.sharkz.model;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named(value="search")
@RequestScoped
public class Search {
    public Search() {
        super();
    }
    
    @Override
    public String toString() {
        return "Suche";
    }
}
