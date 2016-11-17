package de.othr.sw.sharkz.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class ServicePrototype {
    @PersistenceContext(name="SharkzPU")
    EntityManager em;
}
