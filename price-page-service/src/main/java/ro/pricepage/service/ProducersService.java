package ro.pricepage.service;

import ro.pricepage.persistence.entities.Producer;
import ro.pricepage.qualifiers.MySQLDatabase;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
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

    @Inject
    @MySQLDatabase
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

    public Producer get(Integer id){
        return (Producer) em.createNamedQuery(Producer.GET_PRODUCER_BY_ID).setParameter("id", id).getSingleResult();
    }

    public List<Producer> list(){
        return em.createNamedQuery(Producer.GET_PRODUCERS).getResultList();
    }
}
