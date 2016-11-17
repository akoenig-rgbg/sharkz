package de.othr.sw.sharkz.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class Address implements Serializable {
    private String postCode;
    private String town;
    private String street;
    private int houseNumber;
    private char additionalLetter;

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public char getAdditionalLetter() {
        return additionalLetter;
    }

    public void setAdditionalLetter(char additionalLetter) {
        this.additionalLetter = additionalLetter;
    }
}