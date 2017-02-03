package de.othr.sw.sharkz.model;

import de.othr.sw.sharkz.entity.Order;
import de.othr.sw.sharkz.service.SearchService;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named(value="home")
public class IndexModel {
    
    // Constants
    private static final int WRAPPER_WIDTH = 1040;
    private static final int FETCH_AMOUNT = 3;
    
    // Featured insertions
    private List<Order> luxuries;
    
    // Models & Services
    @Inject SearchService searchService;
    
    @PostConstruct
    public void init() {
        luxuries = searchService.fetchLuxury(FETCH_AMOUNT);
    }
    
    public String search() {
        return "search";
    }
    
    public List<Order> fetchLuxury() {
        return searchService.fetchLuxury(FETCH_AMOUNT);
    }
    
    public List<Order> fetchBusiness() {
        return searchService.fetchBusinessOrders(FETCH_AMOUNT);
    }
    
    public int calcCardWidth() {
        int widthNoPadding = WRAPPER_WIDTH / FETCH_AMOUNT;
        
        return widthNoPadding - (int) (0.1 * widthNoPadding);
    }
    
    // Getter & Setter
    public int getFetchAmount() {
        return FETCH_AMOUNT;
    }

    public List<Order> getLuxuries() {
        return luxuries;
    }

    public void setLuxuries(List<Order> luxuries) {
        this.luxuries = luxuries;
    }

    public String getCopyright() {
        return "&copy; " + Calendar.getInstance().get(Calendar.YEAR)
                + " Andreas König - CEO @ Sharkz AG :P";
    }
}
