package de.othr.sw.sharkz.entity;

import de.othr.sw.sharkz.entity.type.OfferType;
import java.io.File;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class BasicInsertion extends Product {
    @Enumerated(EnumType.STRING)
    private OfferType offerType;

    private String description;
    private Address address;
    private long price;
    
    @ElementCollection
    private List<File> images;
   
    @ManyToOne
    private Customer vendor;

    public BasicInsertion() {
        super();
    }
    
    public BasicInsertion(long pricePerMonth, OfferType offerType,
            String description, Address address, long price,
            List<File> images, Customer vendor) {
        super(pricePerMonth);
        
        this.offerType = offerType;
        this.description = description;
        this.address = address;
        this.images = images;
        this.vendor = vendor;
    }
    
    public OfferType getOfferType() {
        return offerType;
    }

    public void setOfferType(OfferType offerType) {
        this.offerType = offerType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public List<File> getImages() {
        return images;
    }

    public void setImages(List<File> images) {
        this.images = images;
    }

    public Customer getVendor() {
        return vendor;
    }

    public void setVendor(Customer vendor) {
        this.vendor = vendor;
    }
}