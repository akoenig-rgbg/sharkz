package de.othr.sw.sharkz.service.web;

import de.othr.sw.sharkz.entity.*;
import de.othr.sw.sharkz.entity.type.HouseType;
import de.othr.sw.sharkz.entity.type.OfferType;
import java.io.File;
import java.util.List;

public class InsertionWebObject extends EntityPrototype {
    
    // Attributes
    private HouseType houseType;
    private OfferType offerType;

    private String description;
    private Address address;
    private long price;
    
    private List<File> images;
   
    private Customer vendor;
    
    private InsertionAttributesIF insertionAttributes;
    
    // Constructors
    public InsertionWebObject() {
        super();
    }
    
    // Getters & Setters
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

    public HouseType getHouseType() {
        return houseType;
    }

    public void setHouseType(HouseType houseType) {
        this.houseType = houseType;
    }

    public InsertionAttributesIF getInsertionAttributes() {
        return insertionAttributes;
    }

    public void setInsertionAttributes(InsertionAttributesIF insertionAttributes) {
        this.insertionAttributes = insertionAttributes;
    }
}