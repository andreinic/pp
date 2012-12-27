package ro.pricepage.service;

import ro.pricepage.persistence.entities.ProductCategory;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

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

    public Set<ProductCategory> list(){
        List<ProductCategory> results = em.createNamedQuery(GET_CATEGORIES).getResultList();
        Set<ProductCategory> ret = new HashSet<>();
        Iterator<ProductCategory> it = results.iterator();
        while(it.hasNext()){
            ProductCategory pc = it.next();
            if(pc.getParent() != null) break;
            ret.add(pc);
        }
        return ret;
    }

    public List<ProductCategory> listParents(){
        return (List<ProductCategory>) em.createNamedQuery(GET_PARENT_CATEGORIES).getResultList();
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

    public ProductCategory update(Integer id, String name) throws Exception{
        ProductCategory productCategory = em.find(ProductCategory.class, id);
        if(productCategory == null) throw new Exception("Unable to find entity to update");
        if(!productCategory.getName().equalsIgnoreCase(name.trim())) productCategory.setName(name);
        return productCategory;
    }

    public void delete(ProductCategory category){
        category.getParent().getChildren().remove(category);
        em.remove(em.find(ProductCategory.class, category.getId()));
    }
}
