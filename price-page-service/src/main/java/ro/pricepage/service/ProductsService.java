package ro.pricepage.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import ro.pricepage.persistence.entities.Product;
import ro.pricepage.persistence.entities.ProductStore;
import ro.pricepage.qualifiers.MySQLDatabase;

@Named(value = "productsService")
@Stateless
public class ProductsService extends BaseService
{
    private static final long serialVersionUID = 1L;
    @Inject
    @MySQLDatabase
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Product> list(int start, int end){
        assert start > end : "start must be greater than end";
        List<Product> products = em.createNamedQuery(Product.GET_PRODUCTS, Product.class).setMaxResults(end - start).setFirstResult(start).getResultList();
        return products;
    }

    public List<Object[]> getAggregatedProducts(int start, int end){
        return em.createQuery("SELECT p.id, p.name, MIN(ps.price) FROM ProductStore ps JOIN ps.product p GROUP BY ps.price", Object[].class).setMaxResults(end - start).setFirstResult(start).getResultList();
    }

    public List<Object[]> getAggregatedProductsByCateg(int categoryId, int start, int end){
        return em.createQuery("SELECT p.id, p.name, MIN(ps.price) " +
                              "FROM ProductStore ps JOIN ps.product p " +
                              "WHERE p.category.id IN" +
                                    "(SELECT c.id FROM ProductCategory c, ProductCategory c1 WHERE c1.id=:categId AND c.rgt-c.lft=1) " +
                              "GROUP BY ps.price", Object[].class).setParameter("categId", categoryId).setMaxResults(end - start).setFirstResult(start).getResultList();
    }

    public Integer countForCateg(int categoryId){
        return em.createNamedQuery(Product.COUNT_PRODUCTS_FOR_CATEGORY).setParameter("catId", categoryId).getFirstResult();
    }

    public List<Object[]> getAggregatedProductsByStoreType(int storeTypeId, int start, int end){
        return em.createQuery("SELECT p.id, p.name, MIN(ps.price) " +
                              "FROM ProductStore ps " +
                              "JOIN ps.product p " +
                              "WHERE ps.store.id IN (SELECT s.id FROM Store s JOIN s.storeType st WHERE st.id = :storeTypeId)", Object[].class)
                 .setParameter("storeTypeId", storeTypeId)
                 .setFirstResult(start)
                 .setMaxResults(end - start)
                 .getResultList();
    }

    public List<Object[]> getAggregatedProductsByStoreChain(int storeChainId, int start, int end){
        return em.createQuery("SELECT p.id, p.name, MIN(ps.price) FROM ProductStore ps JOIN ps.product p WHERE ps.store.id IN (SELECT s.id FROM Store s WHERE s.chain.id = :storeChainId)")
                 .setParameter("storeChainId", storeChainId)
                 .setMaxResults(end - start)
                 .setFirstResult(start)
                 .getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Product> list(int categoryId, int start, int end){
        List<Product> products = em.createNamedQuery(Product.GET_PRODUCTS_FOR_PARENT_CATEGORY, Product.class)
                                   .setMaxResults(end - start)
                                   .setFirstResult(start)
                                   .setParameter("parentId", categoryId)
                                   .getResultList();
        return products;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Product get(int id){
        return em.createNamedQuery(Product.GET_PRODUCT_BY_ID, Product.class).setParameter("productId", id).getSingleResult();
    }

    public List<Object[]> getProductDetails(int id){
        return em.createQuery("SELECT s.id, s.longitude, s.latitude, s.address, sc.name, ps.price, s.zip FROM StoreChain sc JOIN sc.stores s JOIN s.productInstances ps WHERE ps.product.id = :productId ORDER BY ps.price", Object[].class).setParameter("productId", id).getResultList();
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ProductStore> getAllInstancesForProduct(int id){
    	TypedQuery<ProductStore> q = em.createNamedQuery(ProductStore.FIND_ALL_FOR_PRODUCT_ID, ProductStore.class);
    	q.setParameter("id", id);
    	return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Long count(){
        return (Long) em.createNamedQuery(Product.COUNT_PRODUCTS).getSingleResult();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Product add(Product product){
        assert product != null : "cannot pass null value for product";
        em.persist(product);
        return product;
    }
}
