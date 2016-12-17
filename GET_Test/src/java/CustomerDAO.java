import java.util.HashMap;

public class CustomerDAO {
    static HashMap<String, Customer> data;
     
    static {
        data = new HashMap<String, Customer>();
         
        data.put("E001", new Customer("E001", "Bugs Bunny"));
        data.put("E002", new Customer("E002", "Daffy Duck"));
        data.put("E003", new Customer("E003", "Samity Sam"));
    }
     
    public Customer findCustomer(String id) {
        return data.get(id);
    }
}
