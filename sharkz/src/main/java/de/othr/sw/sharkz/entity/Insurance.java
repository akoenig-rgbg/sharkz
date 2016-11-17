package de.othr.sw.sharkz.entity;

import de.othr.sw.sharkz.entity.type.InsuranceType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Insurance extends Product {
    @Enumerated(EnumType.STRING)
    private InsuranceType type;

    public Insurance(InsuranceType type, long price) {
        super(price);
        this.type = type;
    }

    public InsuranceType getType() {
        return type;
    }

    public void setType(InsuranceType type) {
        this.type = type;
    }
}
