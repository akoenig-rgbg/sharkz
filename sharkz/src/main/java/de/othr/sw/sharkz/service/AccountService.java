package de.othr.sw.sharkz.service;

import de.othr.sw.sharkz.entity.Account;
import de.othr.sw.sharkz.entity.Administrator;
import de.othr.sw.sharkz.entity.BankingData;
import de.othr.sw.sharkz.entity.Customer;
import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.entity.Message;
import de.othr.sw.sharkz.entity.util.EntityUtils;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

@Named
@RequestScoped
public class AccountService extends ServicePrototype implements Serializable {
    
    @Inject private InsertionService insertionService;
    
    /**
     * Create and persist a new Customer
     * @param customer the customer to persist
     * @return id of the Customer
     */
    @Transactional(TxType.REQUIRED)
    public long createCustomer(Customer customer) {
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
     * Update the passwort of an account
     * @param userId the id of the account
     * @param password the new password
     */
    @Transactional(TxType.REQUIRED)
    public void updatePassword(long userId, String password) {
        Account c = em.find(Account.class, userId);
        
        c.setPassword(password);
        em.merge(c);
    }
    
    /**
     * Find a customer by its id
     * @param id the id
     * @return the customer
     */
    public Customer findCustomer(long id) {
        return em.find(Customer.class, id);
    }
    
    /**
     * Find an account by email
     * @param email the email
     * @return the account with the email
     */
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
    
    /**
     * Get the full name of an account
     * @param id the id of the account
     * @return Administrator in case of an admin account
     *         the full name else
     */
    public String getNameByID(long id) {        
        Customer c = em.find(Customer.class, id);
        
        if (c.getFirstName() != null && c.getLastName() != null) {
            return c.getFirstName() + " " + c.getLastName();
        } else {
            return "Administrator";
        }
    }
    
    /**
     * Updates the general information about a customer
     * @param userId
     * @param firstName
     * @param lastName
     * @param phoneNumber
     * @param email 
     */
    @Transactional(TxType.REQUIRED)
    public void updateCustomerData(long userId, String firstName,
            String lastName, String phoneNumber, String email) {
        
        Customer c = em.find(Customer.class, userId);
        
        c.setFirstName(firstName);
        c.setLastName(lastName);
        c.setPhoneNumber(phoneNumber);
        c.seteMail(email);
        
        em.merge(c);
    }
    
    /**
     * Updates the banking data of a customer
     * @param userId
     * @param iban
     * @param bic
     * @param password 
     */
    @Transactional(TxType.REQUIRED)
    public void updateBankingData(long userId, String iban, String bic,
            String password) {
        
        Customer c = em.find(Customer.class, userId);
        
        BankingData data = new BankingData();
        
        data.setBic(bic);
        data.setIban(iban);
        data.setPassword(password);
        
        c.setBankingData(data);
        
        em.merge(c);
        em.flush();
    }
    
    /**
     * Adds an insertion to a customer's wishlist
     * @param userId
     * @param ins 
     */
    @Transactional(TxType.REQUIRED)
    public void addToWishlist(long userId, Insertion ins) {
        Customer c = (Customer) em.find(Customer.class, userId);
        Insertion in = em.find(Insertion.class, ins.getID());
        
        c.getWishList().add(in);
        
        em.merge(c);
        em.flush();
    }
    
    /**
     * Deletes an insertion from a customer's wishlist
     * @param userId
     * @param ins 
     */
    @Transactional(TxType.REQUIRED)
    public void deleteFromWishlist(long userId, Insertion ins) {
        Customer c = (Customer) em.find(Customer.class, userId);
        Insertion in = em.find(Insertion.class, ins.getID());
        
        c.getWishList().remove(in);
        
        em.merge(c);
        em.flush();
    }
    
    /**
     * Deletes a message from a customer's inbox
     * @param messageId 
     */
    @Transactional(TxType.REQUIRED)
    public void deleteMessage(long messageId) {
        Message msg = em.find(Message.class, messageId);
        
        TypedQuery<Customer> q = em.createQuery(
                "SELECT c FROM Customer AS c WHERE :msg MEMBER OF c.messages",
                Customer.class)
                .setParameter("msg", msg);
        
        Customer c = q.getSingleResult();
        
        c.getMessages().remove(msg);
        em.merge(c);
        
        em.remove(msg);
        em.flush();
    }
    
    /**
     * Adds a message to a customer's inbox
     * @param senderId
     * @param receiverId
     * @param insertionId
     * @param msg 
     */
    @Transactional(TxType.REQUIRED)
    public void addMessage(long senderId, long receiverId, long insertionId,
            Message msg) {
        
        Customer sender = em.find(Customer.class, senderId);
        Customer receiver = em.find(Customer.class, receiverId);
        Insertion in = em.find(Insertion.class, insertionId);
        
        msg.setSender(sender);
        msg.setInsertion(in);
        
        em.persist(msg);
        
        receiver.getMessages().add(msg);
        em.merge(receiver);
        
        em.flush();
    }
    
    /**
     * Finds a message
     * @param msgId
     * @return 
     */
    public Message findMessage(long msgId) {
        return em.find(Message.class, msgId);
    }
    
    /**
     * Fetch a list with all registered customers.
     * @return 
     */
    public List<Customer> fetchAllCustomers() {
        TypedQuery<Customer> q = em.createQuery(
                "SELECT cus FROM Customer AS cus",
                Customer.class);
        
        return q.getResultList();
    }
    
    @Transactional(TxType.REQUIRED)
    public void deleteCustomer(long customerId) {
        Customer c = em.find(Customer.class, customerId);
        
        for (Insertion in : c.getInsertions())
            insertionService.deleteInsertion(in.getID());
        
        em.remove(c);
    }
    
    public String getNumOfCustomers() {
        Query q = em.createQuery("SELECT COUNT(cus) FROM Customer AS cus");
        
        return String.valueOf(q.getSingleResult());
    }
}
