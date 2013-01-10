package ro.pricepage.producers;

import ro.pricepage.qualifiers.MySQLDatabase;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

public class EntityManagerProducer implements Serializable
{
    private static final long serialVersionUID = 1L;

    @PersistenceContext
    @Produces
    @MySQLDatabase
    private EntityManager em;
}
