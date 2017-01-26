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
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

@Named
@RequestScoped
public class AccountService extends ServicePrototype implements Serializable {
    
    
    
    /**
     * Create and persist a new Customer
     * @param customer the customer to persist
     * @return id of the Customer
     */
    @Transactional(TxType.REQUIRED)
    public long createCustomer(Customer customer) {
        em.persist(customer.getInbox());
        em.persist(customer);
  
        return customer.getID();
    }
    
    /**
     * Create a new Administrator account
     * @param eMail the email of the admin
     * @param password the password for the login
     * @return id of the Administrator
     */
    @Transactional(TxType.REQUIRED)
    public long createAdministrator(String eMail, String password) {
        Administrator admin = new Administrator();
        
        admin.setPassword(password);
        admin.seteMail(eMail);
        
        em.persist(admin);
        em.flush();
        
        return admin.getID();
    }
    
    /**
     * Checks if password matches email
     * @param email
     * @param password
     * @return true if the password is right
     *         false otherwise
     */
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
    
    /**
     * Find a customer by its id
     * @param id the id
     * @return the customer
     */
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
        
        try {
            Account acc = (Account) q.getSingleResult();
            return acc;
        } catch (NoResultException e) {
            return null;
        }
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
    public void addToWishlist(Account acc, Insertion ins) {
        Customer customer = (Customer) em.merge(acc);
        
        customer.getWishList().add(em.merge(ins));
        
        em.merge(customer);
        em.flush();
    }
    
    @Transactional(TxType.REQUIRED)
    public void deleteMessage(long receiverId, Message msg) {
        
        Customer receiver = em.find(Customer.class, receiverId);
        
        receiver.getInbox().getMessages().remove(em.merge(msg));
        em.merge(receiver);
        
        em.remove(em.merge(msg));
    }
    
    @Transactional(TxType.REQUIRED)
    public void addMessage(Customer sender, Customer receiver,
            Insertion reference, Message msg) {
        msg.setSender(em.merge(sender));
        msg.setInsertion(em.merge(reference));
        
        receiver = em.merge(receiver);
        receiver.getInbox().getMessages().add(msg);
        
        em.merge(receiver);
    }
    
    public Message findMessage(long msgId) {
        return em.find(Message.class, msgId);
    }
}
