package de.othr.sw.sharkz.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class EntityPrototype implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    
    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        
        if (getClass() != o.getClass()) {
            return false;
        }
        
        final EntityPrototype other = (EntityPrototype) o;
        
        if (!Objects.equals(id, other.getID())) {
            return false;
        }
        
        return true;
    }
    
    public long getID() {
        return id;
    }
}
