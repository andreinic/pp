package ro.pricepage.persistence;

import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

/**
 * User: radutoev
 * Date: 12.11.2012
 * Time: 20:55
 */
@Named(value = "entityManagerProducer")
//@SessionScoped
public class EntityManagerProducer implements Serializable
{
    private static final long serialVersionUID = 1L;

    /* This permits CDI injection style of entity manager*/
    @PersistenceContext(unitName="pricePagePU")
    @Produces
    private EntityManager entityManager;
}
