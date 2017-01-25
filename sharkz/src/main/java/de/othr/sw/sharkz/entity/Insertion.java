package de.othr.sw.sharkz.entity;

import de.othr.sw.sharkz.entity.type.HouseType;
import de.othr.sw.sharkz.entity.type.OfferType;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class Insertion extends EntityPrototype {
    
    // Attributes
    @Enumerated(EnumType.STRING)
    private HouseType houseType;
    
    @Enumerated(EnumType.STRING)
    private OfferType offerType;

    private String title;
    private String description;
    private Address address;
    private long price;
    
    @ElementCollection(fetch = FetchType.LAZY)
    @Column(length= 5 * 3200000)
    private List<byte[]> images;
   
    @ManyToOne
    private Customer vendor;
    
    // Constructors
    public Insertion() {
        super();
    }
    
    Map<String, String> getAttributes() {
        Map<String, String> attrs = new LinkedHashMap<>();
        
        attrs.put("Erwerbstyp", this.getOfferType().getLabel());
        attrs.put("Immobilientyp", this.getHouseType().getLabel());
        
        return attrs;
    }
    
    public abstract Map<String, String> getImportantAttributes();
    
    public abstract Map<String, String> getFurtherAttributes();
    
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

    public List<byte[]> getImages() {
        return images;
    }

    public void setImages(List<byte[]> images) {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
}