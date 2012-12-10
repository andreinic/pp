package ro.pricepage.service;

import ro.pricepage.persistence.entities.ProductCategory;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static ro.pricepage.persistence.entities.ProductCategory.*;

/**
 * @author  radutoev
 */
@Named(value = "productCategoriesService")
@Stateless
public class ProductCategoriesService extends BaseService
{
    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    public ProductCategory add(String name){
        assert name != null && !"".equals(name) : "unsupported parameter value provided";
        ProductCategory pc = new ProductCategory();
        pc.setName(name);
        em.persist(pc);
        return pc;
    }

    public List<ProductCategory> list(){
        return (List<ProductCategory>) em.createNamedQuery(GET_CATEGORIES).getResultList();
    }
}
