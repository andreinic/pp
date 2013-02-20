package ro.pricepage.service;

import static ro.pricepage.persistence.entities.ProductCategory.GET_ALL_CATEGORIES;
import static ro.pricepage.persistence.entities.ProductCategory.GET_CATEGORIES;
import static ro.pricepage.persistence.entities.ProductCategory.GET_CATEGORY_BY_ID;
import static ro.pricepage.persistence.entities.ProductCategory.GET_CATEGORY_HIERARCHY;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import ro.pricepage.persistence.entities.Product;
import ro.pricepage.persistence.entities.ProductCategory;
import ro.pricepage.qualifiers.MySQLDatabase;

/**
 * @author  radutoev
 */
@Named(value = "productCategoriesService")
@Stateless
public class ProductCategoriesService extends BaseService
{
	private static final long serialVersionUID = 1L;

	@Inject
	@MySQLDatabase
	private EntityManager em;

	public Set<ProductCategory> hierarchy(){
		@SuppressWarnings("unchecked")
		List<ProductCategory> results = em.createNamedQuery(GET_CATEGORY_HIERARCHY).getResultList();
		Set<ProductCategory> ret = new HashSet<>();
		Iterator<ProductCategory> it = results.iterator();
		while(it.hasNext()){
			ProductCategory pc = it.next();
			ret.add(pc);
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	public List<ProductCategory> list(){
		return (List<ProductCategory>) em.createNamedQuery(GET_CATEGORIES).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<ProductCategory> listAll(){
		return (List<ProductCategory>) em.createNamedQuery(GET_ALL_CATEGORIES).getResultList();
	}

	public ProductCategory getById(Integer id){
		TypedQuery<ProductCategory> query = em.createNamedQuery(GET_CATEGORY_BY_ID, ProductCategory.class).setParameter("id", id).setMaxResults(1);
		try{
			return query.getSingleResult();
		} catch (NoResultException e){
			return null;
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public ProductCategory add(String name, ProductCategory parent){
		assert name != null && !"".equals(name) : "unsupported parameter value provided";
		ProductCategory pc = new ProductCategory();
		pc.setName(name);
		pc.setParent(parent);
		parent.getChildren().add(pc);
		em.persist(pc);
		em.merge(parent);
		return pc;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public ProductCategory merge(ProductCategory pc){
		ProductCategory merged = em.merge(pc);
		return merged;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public ProductCategory update(Integer id, String name) throws Exception{
		ProductCategory productCategory = em.find(ProductCategory.class, id);
		if(productCategory == null) throw new Exception("Unable to find entity to update");
		if(!productCategory.getName().equalsIgnoreCase(name.trim())) productCategory.setName(name);
		return productCategory;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void delete(ProductCategory category){
		if(category.getParent() != null){
			category.getParent().getChildren().remove(category);
		}
		em.remove(em.find(ProductCategory.class, category.getId()));
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean hasProducts(Integer id){
		Query q = em.createNamedQuery(Product.COUNT_PRODUCTS_FOR_CATEGORY);
		q.setParameter("catId", id);
		Long c = (Long) q.getSingleResult();
		return c.intValue() > 0;
	}
}
