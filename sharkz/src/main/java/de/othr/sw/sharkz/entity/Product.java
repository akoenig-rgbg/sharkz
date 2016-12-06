package de.othr.sw.sharkz.entity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Product extends EntityPrototype {
    private long pricePerMonth;

    public Product() {
        super();
    }
    
    public Product(long price) {
        pricePerMonth = price;
    }
    
    public long getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(long pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }
}
