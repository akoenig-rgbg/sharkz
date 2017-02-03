package de.othr.sw.sharkz.model.account.admin;

import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.service.AccountService;
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
    
    @PostConstruct
    public void init() {
        customers = accountService.fetchAllCustomers();
        
        if (customers.isEmpty())
            hasNoResults = true;
    }

    public void delete(Customer c) {
        accountService.deleteCustomer(c.getID());
    }
    
    public String getName(Customer c) {
        return c.getFirstName() + " " + c.getLastName();
    }
    
    public String getPhoneNumber(Customer c) {
        if (!(c.getPhoneNumber() == null || c.getPhoneNumber().equals("")))
            return c.getPhoneNumber();
        
        return "-";
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
