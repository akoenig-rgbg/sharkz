package de.othr.sw.sharkz.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Order extends EntityPrototype {
    @OneToMany
    private Customer customer;
    
    @OneToMany
    @ElementCollection
    private List<OrderItem> items;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
