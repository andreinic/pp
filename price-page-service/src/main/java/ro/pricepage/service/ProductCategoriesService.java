package ro.pricepage.service;

import ro.pricepage.persistence.entities.ProductCategory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * User: radutoev
 * Date: 30.11.2012
 * Time: 22:46
 */
public class ProductCategoriesService extends BaseService
{
    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    public List<ProductCategory> listProductCategories(){
        return em.createNamedQuery(ProductCategory.GET_CATEGORIES).getResultList();
    }
}
