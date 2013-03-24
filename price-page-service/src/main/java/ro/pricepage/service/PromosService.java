package ro.pricepage.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import ro.pricepage.persistence.entities.Promo;
import ro.pricepage.qualifiers.MySQLDatabase;

@Named(value = "promosService")
@Stateless
public class PromosService extends BaseService {
	private static final long serialVersionUID = -7740364377444357250L;

	@Inject
	@MySQLDatabase
	private EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Promo> getPromosForProductStore(Integer productStoreId,
			Integer start, Integer end) {
		assert start.intValue() > end.intValue() : "start must be greater than end";
		TypedQuery<Promo> q = em
				.createNamedQuery(Promo.Q_FIND_FOR_PRODUCT_STORE_ID, Promo.class)
				.setMaxResults(end.intValue() - start.intValue())
				.setFirstResult(start.intValue());
		q.setParameter("productStoreId", productStoreId);
		return q.getResultList();
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Long countInstancesForProductStore(Integer productStoreId) {
		Query q = em.createNamedQuery(Promo.COUNT_FOR_PRODUCT_STORE_ID);
		q.setParameter("productStoreId", productStoreId);
		return (Long) q.getSingleResult();
	}
}
