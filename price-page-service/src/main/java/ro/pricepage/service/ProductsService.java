package ro.pricepage.service;

import java.util.List;

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
import ro.pricepage.persistence.entities.ProductStore;
import ro.pricepage.qualifiers.MySQLDatabase;

@Named(value = "productsService")
@Stateless
public class ProductsService extends BaseService {
	private static final long serialVersionUID = 1L;
	@Inject
	@MySQLDatabase
	private EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Product> list(int start, int end) {
		assert start > end : "start must be greater than end";
		List<Product> products = em
				.createNamedQuery(Product.GET_PRODUCTS, Product.class)
				.setMaxResults(end - start).setFirstResult(start)
				.getResultList();
		return products;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ProductStore> getInstancesForProduct(int productId, int start,
			int end) {
		assert start > end : "start must be greater than end";
		TypedQuery<ProductStore> q = em
				.createNamedQuery(ProductStore.FIND_ALL_FOR_PRODUCT_ID,
						ProductStore.class).setMaxResults(end - start)
				.setFirstResult(start);
		q.setParameter("id", Integer.valueOf(productId));
		return q.getResultList();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Long countInstancesForProduct(int productId) {
		Query q = em.createNamedQuery(ProductStore.COUNT_FOR_PRODUCT_ID);
		q.setParameter("id", Integer.valueOf(productId));
		return (Long) q.getSingleResult();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Object[]> getAggregatedProducts(int start, int end) {
		return em
				.createQuery(
						"SELECT p.id, p.name, MIN(ps.price) FROM ProductStore ps JOIN ps.product p GROUP BY ps.price",
						Object[].class).setMaxResults(end - start)
				.setFirstResult(start).getResultList();
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Object[]> getAggregatedProductsByCateg(int categoryId,
			int start, int end) {
		StringBuilder sb = new StringBuilder(
				"SELECT p.id, MIN(p.name), MIN(ps.price) ");
		sb.append("FROM products_stores ps ");
		sb.append("INNER JOIN products p ");
		sb.append("ON ps.fk_product = p.id ");
		sb.append("WHERE p.id IN ");
		sb.append("(SELECT id ");
		sb.append("FROM products ");
		sb.append("WHERE fk_category = ");
		sb.append(categoryId);
		sb.append(" OR fk_category IN ");
		sb.append("(SELECT fk_child ");
		sb.append("FROM product_categories_hierarchy ");
		sb.append("WHERE fk_ancestor = ");
		sb.append(categoryId);
		sb.append(") ");
		sb.append(") GROUP BY p.id");
		Query q = em.createNativeQuery(sb.toString());
		q.setFirstResult(start);
		q.setMaxResults(end - start);
		return q.getResultList();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Object[]> getAggregatedProductsByStoreType(int storeTypeId,
			int start, int end) {
		return em
				.createQuery(
						"SELECT p.id, p.name, MIN(ps.price) "
								+ "FROM ProductStore ps "
								+ "JOIN ps.product p "
								+ "WHERE ps.store.id IN (SELECT s.id FROM Store s JOIN s.storeType st WHERE st.id = :storeTypeId)",
						Object[].class)
				.setParameter("storeTypeId", storeTypeId).setFirstResult(start)
				.setMaxResults(end - start).getResultList();
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<Object[]> getAggregatedProductsByStoreChain(int storeChainId,
			int start, int end) {
		return em
				.createQuery(
						"SELECT p.id, p.name, MIN(ps.price) FROM ProductStore ps JOIN ps.product p WHERE ps.store.id IN (SELECT s.id FROM Store s WHERE s.chain.id = :storeChainId)")
				.setParameter("storeChainId", storeChainId)
				.setMaxResults(end - start).setFirstResult(start)
				.getResultList();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Product> list(int categoryId, int start, int end) {
		List<Product> products = em
				.createNamedQuery(Product.GET_PRODUCTS_FOR_PARENT_CATEGORY,
						Product.class).setMaxResults(end - start)
				.setFirstResult(start).setParameter("parentId", categoryId)
				.getResultList();
		return products;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Product get(int id) {
		return em.createNamedQuery(Product.GET_PRODUCT_BY_ID, Product.class)
				.setParameter("productId", id).getSingleResult();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ProductStore getInstance(int id) {
		TypedQuery<ProductStore> q = em.createNamedQuery(
				ProductStore.GET_BY_ID, ProductStore.class);
		q.setParameter("id", id);
		return q.getSingleResult();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Object[]> getProductDetails(int id) {
		return em
				.createQuery(
						"SELECT s.id, s.longitude, s.latitude, s.address, sc.name, ps.price, s.zip FROM StoreChain sc JOIN sc.stores s JOIN s.productInstances ps WHERE ps.product.id = :productId ORDER BY ps.price",
						Object[].class).setParameter("productId", id)
				.getResultList();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ProductStore> getAllInstancesForProduct(int id) {
		TypedQuery<ProductStore> q = em.createNamedQuery(
				ProductStore.FIND_ALL_FOR_PRODUCT_ID, ProductStore.class);
		q.setParameter("id", id);
		return q.getResultList();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Long count() {
		return (Long) em.createNamedQuery(Product.COUNT_PRODUCTS)
				.getSingleResult();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Integer countForCateg(int categoryId) {
		return em.createNamedQuery(Product.COUNT_PRODUCTS_FOR_CATEGORY)
				.setParameter("catId", categoryId).getFirstResult();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Product add(Product product) {
		assert product != null : "cannot pass null ref for product";
		em.persist(product);
		return product;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Product update(Product p) {
		assert p != null : "cannot pass null ref for product";
		return em.merge(p);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public ProductStore add(ProductStore ps) {
		assert ps != null : "cannot pass null ref for product store";
		em.persist(ps);
		return ps;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public ProductStore update(ProductStore ps) {
		assert ps != null : "cannot pass null ref for product store";
		return em.merge(ps);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void deleteProductStore(ProductStore ps) {
		Product p = ps.getProduct();
		em.merge(p);
		ps = em.merge(ps);
		em.remove(ps);
		em.merge(p);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void deleteProduct(Product p) {
		em.remove(em.merge(p));
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ProductStore getPriceForStoreAndProduct(Integer storeId,
			Integer productId) {
		TypedQuery<ProductStore> q = em
				.createNamedQuery(ProductStore.GET_BY_PRODUCT_ID_AND_STORE_ID,
						ProductStore.class);
		q.setParameter("storeId", storeId);
		q.setParameter("productId", productId);
		try {
			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
