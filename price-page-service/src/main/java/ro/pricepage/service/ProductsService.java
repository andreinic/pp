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

    public List<Product> list(int start, int end){
        assert start > end : "start must be greater than end";
        List<Product> products = em.createNamedQuery(Product.GET_PRODUCTS, Product.class).setMaxResults(end - start).setFirstResult(start).getResultList();
        return products;
    }

    public List<Product> list(int categoryId, int start, int end){
        List<Product> products = em.createNamedQuery(Product.GET_PRODUCTS_FOR_PARENT_CATEGORY, Product.class)
                                   .setMaxResults(end - start)
                                   .setFirstResult(start)
                                   .setParameter("parentId", categoryId)
                                   .getResultList();
        return products;
    }

    public Product get(int id){
        return em.createNamedQuery(Product.GET_PRODUCT_BY_ID, Product.class).setParameter("productId", id).getSingleResult();
    }
    
    public List<ProductStore> getAllInstancesForProduct(int id){
    	TypedQuery<ProductStore> q = em.createNamedQuery(ProductStore.FIND_ALL_FOR_PRODUCT_ID, ProductStore.class);
    	q.setParameter("id", id);
    	return q.getResultList();
    }

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
