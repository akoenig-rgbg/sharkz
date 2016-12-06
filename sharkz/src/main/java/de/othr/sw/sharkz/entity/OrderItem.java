package de.othr.sw.sharkz.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class OrderItem extends EntityPrototype {
    @ManyToOne
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
