package ro.pricepage.producers;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ro.pricepage.qualifiers.MySQLDatabase;

@Stateless
@Named("emProducer")
public class EntityManagerProducer implements Serializable
{
    private static final long serialVersionUID = 1L;

    @PersistenceContext
    @Produces
    @MySQLDatabase
    private static EntityManager em;
    
    
}
