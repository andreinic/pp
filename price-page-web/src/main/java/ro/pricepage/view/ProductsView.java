package ro.pricepage.view;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import org.jboss.seam.faces.context.RenderScoped;
import org.primefaces.event.SelectEvent;
import ro.pricepage.model.ProductsDataModel;
import ro.pricepage.persistence.entities.Product;
import ro.pricepage.persistence.entities.ProductCategory;
import ro.pricepage.service.ProductCategoriesService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * User: radutoev
 * Date: 27.10.2012
 * Time: 16:22
 */
@Named(value = "productsView")
@RenderScoped
@URLMapping(id = "productsView", pattern = "/admin/produse", viewId = "/WEB-INF/view/admin/products.jsf")
public class ProductsView implements Serializable
{
    private static final long serialVersionUID = 1L;

    private List<ProductCategory> categories;
    private Product product;

    @Inject
    private ProductsDataModel productsModel;

    @Inject
    private ProductCategoriesService categoriesService;


    @PostConstruct
    public void init(){
        product = new Product();
        categories = categoriesService.list();
    }

    // Actions ---------------------------------------------------------------------------------------------
    public void onSave(){
        if(product != null) productsModel.addProduct(product);
        product = new Product();
    }

    // Getters and setters ---------------------------------------------------------------------------------

    public List<ProductCategory> getCategories() { return categories; }
    public void setCategories(List<ProductCategory> categories) { this.categories = categories; }

    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductsDataModel getProductsModel(){ return productsModel; }
}
