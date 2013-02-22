package ro.pricepage.service;

import ro.pricepage.persistence.entities.Product;
import ro.pricepage.qualifiers.MySQLDatabase;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

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
