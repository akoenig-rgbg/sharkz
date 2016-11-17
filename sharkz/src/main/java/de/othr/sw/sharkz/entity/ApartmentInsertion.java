package de.othr.sw.sharkz.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class ApartmentInsertion extends ResidentialInsertion {
    @Enumerated(EnumType.STRING)
    private ApartmentType type;
    private boolean lift;

    public ApartmentType getType() {
        return type;
    }

    public void setType(ApartmentType type) {
        this.type = type;
    }

    public boolean isLift() {
        return lift;
    }

    public void setLift(boolean lift) {
        this.lift = lift;
    }
}
