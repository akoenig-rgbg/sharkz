package de.othr.sw.sharkz.model.account.admin;

import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.service.AccountService;
import de.othr.sw.sharkz.service.InsertionService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ViewScoped
@Named("customerMgmt")
public class CustomerManagementModel implements Serializable {
    private List<Customer> customers;
    private boolean hasNoResults;

    // Models & Services
    @Inject AccountService accountService;
    @Inject InsertionService insertionService;
    
    @PostConstruct
    public void init() {
        customers = accountService.fetchAllCustomers();
        
        if (customers.isEmpty())
            hasNoResults = true;
    }

    /**
     * Delte a customer
     * @param c
     * @return 
     */
    public String delete(Customer c) {
        accountService.deleteCustomer(c.getID());
        
        return "customerManagement";
    }
    
    /**
     * Get the full name of a customer
     * @param c
     * @return 
     */
    public String getName(Customer c) {
        return c.getFirstName() + " " + c.getLastName();
    }
    
    /**
     * Get a customer's phone number
     * @param c
     * @return 
     */
    public String getPhoneNumber(Customer c) {
        if (!(c.getPhoneNumber() == null || c.getPhoneNumber().equals("")))
            return c.getPhoneNumber();
        
        return "-";
    }
    
    /**
     * Fetch a customer's insertions
     * @param c
     * @return 
     */
    public List<Insertion> getInsertionsByCustomer(Customer c) {
        return insertionService.fetchInsertionsByCustomer(c.getID());
    }
    
    // Getter & Setter
    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public boolean isHasNoResults() {
        return hasNoResults;
    }

    public void setHasNoResults(boolean hasNoResults) {
        this.hasNoResults = hasNoResults;
    }
    
    
}
