package de.othr.sw.sharkz.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Orders")
public class Order extends EntityPrototype implements Serializable {
    @NotNull
    @ManyToOne
    private Customer customer;
    
    @NotNull
    private Insertion insertion;
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Insertion getInsertion() {
        return insertion;
    }

    public void setInsertion(Insertion insertion) {
        this.insertion = insertion;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    
}
