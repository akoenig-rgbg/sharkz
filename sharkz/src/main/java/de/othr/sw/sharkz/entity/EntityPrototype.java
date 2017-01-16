package de.othr.sw.sharkz.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
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
        
        int result = Long.compare(id, other.getID());
        
        boolean res = false;
        
        if (result == 0)
            res = true;
            
        return res;
    }
    
    public long getID() {
        return id;
    }
}
