package ro.pricepage.service;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.hibernate.search.MassIndexer;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

import ro.pricepage.persistence.entities.ProductStore;
import ro.pricepage.qualifiers.MySQLDatabase;

@Named(value = "searchService")
@Stateless
@Startup
public class SearchService extends BaseService {
	private static final long serialVersionUID = 1L;

	@Inject
	@MySQLDatabase
	private EntityManager em;
	
	@PostConstruct
	public void rebuildProductIndex() throws InterruptedException{
		FullTextEntityManager fullTextEm = Search.getFullTextEntityManager(em);
		MassIndexer massIndexer = fullTextEm.createIndexer(ProductStore.class);
		massIndexer.purgeAllOnStart(true).startAndWait();
	}
	
}
