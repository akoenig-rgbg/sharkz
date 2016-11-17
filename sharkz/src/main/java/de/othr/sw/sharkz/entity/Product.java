package de.othr.sw.sharkz.entity;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Product extends EntityPrototype {
    private long pricePerMonth;

    public long getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(long pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }
}
