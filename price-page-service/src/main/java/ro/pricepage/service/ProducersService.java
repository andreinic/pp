package ro.pricepage.service;

import ro.pricepage.persistence.entities.Producer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * User: radutoev
 * Date: 30.11.2012
 * Time: 23:06
 */
public class ProducersService extends BaseService
{
    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    public List<Producer> listProducers(){
        return em.createNamedQuery(Producer.GET_PRODUCERS).getResultList();
    }
}
