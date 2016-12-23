package de.othr.sw.sharkz.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Customer extends Account {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    
    public Customer() {
        super();
    }
    
    @OneToMany(mappedBy="customer")
    private List<Order> orders;
    
    @OneToMany
    private List<BasicInsertion> wishList;
    
    @OneToMany(mappedBy="vendor")
    private List<BasicInsertion> insertions;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<BasicInsertion> getWishList() {
        return wishList;
    }

    public void setWishList(List<BasicInsertion> wishList) {
        this.wishList = wishList;
    }

    public List<BasicInsertion> getInsertions() {
        return insertions;
    }

    public void setInsertions(List<BasicInsertion> insertions) {
        this.insertions = insertions;
    }
}
