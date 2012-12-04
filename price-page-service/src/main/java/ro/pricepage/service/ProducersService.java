package ro.pricepage.service;

import ro.pricepage.persistence.entities.Producer;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * User: radutoev
 * Date: 30.11.2012
 * Time: 23:06
 */
@Named(value = "producersService")
@Stateless
public class ProducersService extends BaseService
{
    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    public Producer add(String name){
        Producer producer = new Producer();
        producer.setName(name);
        em.persist(producer);
        return producer;
    }

    public Producer update(Integer id, String name) throws Exception{
        Producer producer = em.find(Producer.class, id);
        if(producer == null) throw new Exception("Unable to find entity to update");
        if(!producer.getName().equalsIgnoreCase(name.trim())){
            producer.setName(name);
        }
        return producer;
    }

    public void delete(Integer id) throws Exception{
        Producer producer = em.find(Producer.class, id);
        if(producer == null) throw new Exception("Unable to find entity to delete");
        em.remove(producer);
    }

    public Producer get(String name){
        return (Producer) em.createNamedQuery(Producer.GET_PRODUCER_BY_NAME).setParameter("name", name).getSingleResult();
    }

    public List<Producer> list(){
        return em.createNamedQuery(Producer.GET_PRODUCERS).getResultList();
    }
}
