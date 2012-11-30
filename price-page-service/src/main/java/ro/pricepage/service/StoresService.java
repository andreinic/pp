package ro.pricepage.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ro.pricepage.persistence.entities.Store;
import ro.pricepage.persistence.entities.StoreChain;
import ro.pricepage.persistence.entities.StoreType;

@Named("storesService")
@Stateless
public class StoresService extends BaseService {
	private static final long serialVersionUID = -2191833772375756341L;

	@PersistenceContext
	private EntityManager em;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Store saveStore(Store store){
		return em.merge(store);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<String> findAllStoreChainNames() {
		TypedQuery<String> q = em.createNamedQuery(StoreChain.Q_FIND_ALL_NAMES,
				String.class);
		return q.getResultList();
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<String> findAllStoreTypeNames() {
		TypedQuery<String> q = em.createNamedQuery(StoreType.Q_FIND_ALL_NAMES,
				String.class);
		return q.getResultList();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public StoreChain findSingleStoreChainByName(String name) {
		TypedQuery<StoreChain> q = em.createNamedQuery(StoreChain.Q_FIND_BY_NAME, StoreChain.class)
				.setParameter("name", name)
				.setMaxResults(1);
		try{
			return q.getSingleResult();
		}
		catch(NoResultException e){
			return null;
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public StoreType findSingleStoreTypeByName(String name) {
		TypedQuery<StoreType> q = em.createNamedQuery(StoreType.Q_FIND_BY_NAME, StoreType.class)
				.setParameter("name", name)
				.setMaxResults(1);
		try{
			return q.getSingleResult();
		}
		catch(NoResultException e){
			return null;
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public StoreChain saveChainWithName(String name){
		StoreChain chain = new StoreChain();
		chain.setName(name);
		return em.merge(chain);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public StoreType saveTypeWithName(String name){
		StoreType type = new StoreType();
		type.setName(name);
		return em.merge(type);
	}
}
