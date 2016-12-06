package de.othr.sw.sharkz.service;

import de.othr.sw.sharkz.entity.Customer;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

@Named
@RequestScoped
public class CustomerService extends ServicePrototype implements Serializable {
  
    @Transactional(TxType.REQUIRED)
    public long createCustomer(String firstName, String lastName, String eMail,
            String password, String phoneNumber) {
        Customer customer = new Customer();
        
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setPassword(password);
        customer.setPhoneNumber(phoneNumber);
        customer.seteMail(eMail);
        
        customer.setOrders(null);
        customer.setWishList(null);
        customer.setInsertions(null);
        
        em.persist(customer);
        em.flush();
        
        return customer.getID();
    }
    
    public Customer findCustomer(long id) {
        return em.find(Customer.class, id);
    }
    
    public List<Customer> findAllCustomers() {
        Query q = em.createNativeQuery("SELECT * FROM CUSTOMER");
        return q.getResultList();
    }
}
