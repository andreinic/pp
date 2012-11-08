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

import ro.pricepage.persistence.StoreChain;

@Named("storesService")
@Stateless
public class StoresService extends BaseService {
	private static final long serialVersionUID = -2191833772375756341L;

	@PersistenceContext
	private EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<String> findAllStoreChainNames() {
		TypedQuery<String> q = em.createNamedQuery(StoreChain.Q_FIND_ALL_NAMES,
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
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public StoreChain saveChainWithName(String name){
		StoreChain chain = new StoreChain();
		chain.setName(name);
		em. persist(chain);
		return chain;
	}
}
