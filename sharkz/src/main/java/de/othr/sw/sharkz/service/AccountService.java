package de.othr.sw.sharkz.service;

import de.othr.sw.sharkz.entity.Account;
import de.othr.sw.sharkz.entity.Administrator;
import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.entity.Message;
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
        em.persist(customer);
  
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
        Query q = em.createNativeQuery("SELECT * FROM Account WHERE DTYPE="
                + "'Customer'");
        return q.getResultList();
    }
    
    public Account getAccountByEmail(String email) {
        Query q = em.createNativeQuery("SELECT * FROM Account WHERE EMAIL='"
                + email + "'", Account.class);
        
        return (Account) q.getSingleResult();
    }
    
    public String getNameByID(long id) {        
        Customer c = em.find(Customer.class, id);
        
        if (c.getFirstName() != null && c.getLastName() != null) {
            return c.getFirstName() + " " + c.getLastName();
        } else {
            return "Administrator";
        }
    }
    
    @Transactional(TxType.REQUIRED)
    public void updateAccount(Account c) {
        em.merge(c);
        em.flush();
    }
    
    @Transactional(TxType.REQUIRED)
    public void addToWishlist(Customer acc, Insertion ins) {
        List<Insertion> wishlist = acc.getWishList();
        wishlist.add(ins);
        acc.setWishList(wishlist);
        
        em.merge(acc);
        em.flush();
    }
    
    @Transactional(TxType.REQUIRED)
    public void deleteMessage(Customer cust, Message msg) {
        cust = em.merge(cust);
        cust.getInbox().getMessages().remove(msg);
        cust = em.merge(cust);
        
        em.remove(em.merge(msg));
    }
    
    public Message findMessage(long msgId) {
        return em.find(Message.class, msgId);
    }
}
