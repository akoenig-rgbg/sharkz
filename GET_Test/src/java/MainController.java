import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;

@ManagedBean(name="mainController")
@RequestScoped
@Named(value="mainController")
public class MainController {
    String lookupId;
    Customer customer = new Customer();
     
    public String getLookupId() {
        return lookupId;
    }
    public void setLookupId(String lookupId) {
        this.lookupId = lookupId;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public String doLookup() {
        CustomerDAO dao = new CustomerDAO();
         
        customer = dao.findCustomer(lookupId);
         
        return "resultTempl?includeViewParams=true&faces-redirect=true";
    }
}

