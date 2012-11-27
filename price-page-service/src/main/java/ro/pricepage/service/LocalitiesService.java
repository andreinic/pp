package ro.pricepage.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ro.pricepage.persistence.entities.Location;


@Named("localitiesService")
@Stateless
public class LocalitiesService extends BaseService {
	private static final long serialVersionUID = -7309042858715539496L;

	@PersistenceContext
	private EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<String> findAllCitiesForCounty(String county) {
		TypedQuery<String> q = em.createNamedQuery(
				Location.Q_FIND_ALL_CITIES_BY_COUNTY, String.class)
				.setParameter("county", county);
		return q.getResultList();
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Location> findAllLocationsForCounty(String county){
		TypedQuery<Location> q = em.createNamedQuery(Location.Q_FIND_ALL_LOCATIONS_BY_COUNTY, Location.class)
				.setParameter("county", county);
		return q.getResultList();
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<String> findAllCounties(){
		TypedQuery<String> q = em.createNamedQuery(Location.Q_FIND_ALL_COUNTIES, String.class);
		return q.getResultList();
	}
}
