package ro.pricepage.service;

import ro.pricepage.persistence.entities.Producer;
import ro.pricepage.qualifiers.MySQLDatabase;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * User: radutoev Date: 30.11.2012 Time: 23:06
 */
@Named(value = "producersService")
@Stateless
public class ProducersService extends BaseService {
	private static final long serialVersionUID = 1L;

	@Inject
	@MySQLDatabase
	private EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Producer add(String name) {
		Producer producer = new Producer();
		producer.setName(name);
		em.persist(producer);
		return producer;
	}

	@Deprecated
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Producer update(Integer id, String name) throws Exception {
		Producer producer = em.find(Producer.class, id);
		if (producer == null)
			throw new Exception("Unable to find entity to update");
		if (!producer.getName().equalsIgnoreCase(name.trim())) {
			producer.setName(name);
		}
		return em.merge(producer);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Producer update(Producer p) throws Exception {
		if (p == null) {
			throw new Exception("Unable to find entity to update");
		}
		return em.merge(p);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void delete(Integer id) throws Exception {
		Producer producer = em.find(Producer.class, id);
		if (producer == null) {
			throw new Exception("Unable to find entity to delete");
		}
		em.remove(producer);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Producer get(String name) {
		try {
			return (Producer) em
					.createNamedQuery(Producer.GET_PRODUCER_BY_NAME)
					.setParameter("name", name).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Producer get(Integer id) {
		return (Producer) em.createNamedQuery(Producer.GET_PRODUCER_BY_ID)
				.setParameter("id", id).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Producer> list() {
		return em.createNamedQuery(Producer.GET_PRODUCERS).getResultList();
	}
}
