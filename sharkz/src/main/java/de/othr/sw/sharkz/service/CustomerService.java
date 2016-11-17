package de.othr.sw.sharkz.service;

import de.othr.sw.sharkz.entity.Customer;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

public class CustomerService extends ServicePrototype {
    
    @Transactional(TxType.REQUIRED)
    public void createCustomer(String firstName, String lastName, String eMail,
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
    }
    
    public Customer findCustomer(long id) {
        return em.find(Customer.class, id);
    }
}
