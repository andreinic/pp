package ro.pricepage.persistence;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * User: radutoev
 * Date: 12.11.2012
 * Time: 20:55
 */
public class Resource
{
    /* This permits CDI injection style of entity manager*/
    @PersistenceContext
    @Produces
    private EntityManager entityManager;
}
