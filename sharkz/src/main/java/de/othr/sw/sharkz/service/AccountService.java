package de.othr.sw.sharkz.service;

import de.othr.sw.sharkz.entity.Account;
import de.othr.sw.sharkz.entity.Administrator;
import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.entity.util.EntityUtils;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

@Named
@RequestScoped
public class AccountService extends ServicePrototype implements Serializable {
  
    @Transactional(TxType.REQUIRED)
    public long createCustomer(Customer customer) {
        System.out.println("Kunde wird persistiert");
        em.persist(customer);
        
        System.out.println("Kunde wurde persistiert");
        
        return customer.getID();
    }
    
    @Transactional(TxType.REQUIRED)
    public long createAdministrator(String eMail, String password) {
        Administrator admin = new Administrator();
        
        admin.setPassword(password);
        admin.seteMail(eMail);
        
        em.persist(admin);
        em.flush();
        
        return admin.getID();
    }
    
    public boolean checkPassword(String email, String password) {
        Account acc = getAccountByEmail(email);
        
        if(acc == null)
            return false;
        try {
            return EntityUtils.hashPassword(password, acc.getSalt(),
                    Account.HASH_ALGORITHM).equals(acc.getPassword());
        } catch (EntityUtils.EntityUtilException ex) {
            throw new RuntimeException("password can not be hashed", ex);
        }
    }
        
    public Customer findCustomer(long id) {
        return em.find(Customer.class, id);
    }
    
    public List<Customer> findAllCustomers() {
        Query q = em.createNativeQuery("SELECT * FROM ACCOUNT WHERE DTYPE="
                + "'Customer'");
        return q.getResultList();
    }
    
    public Account getAccountByEmail(String email) {
        Query q = em.createNativeQuery("SELECT * FROM ACCOUNT WHERE EMAIL='"
                + email + "'", Account.class);
        return (Account) q.getResultList().get(0);
    }
    
    public String getNameByID(long id) {        
        Customer c = em.find(Customer.class, id);
        
        if (c.getFirstName() != null && c.getLastName() != null) {
            return c.getFirstName() + " " + c.getLastName();
        } else {
            return "Administrator";
        }
    }
}
