package ro.pricepage.view;

import com.ocpsoft.pretty.faces.annotation.URLMapping;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

/**
 * User: radutoev
 * Date: 27.10.2012
 * Time: 16:22
 */
@Named(value = "productsView")
@ViewScoped
@URLMapping(id = "productsView", pattern = "/admin/produse", viewId = "/WEB-INF/view/admin/products.jsf")
public class ProductsView
{
    @PostConstruct
    public void init(){
        System.out.println("init products bean");
    }

    public String doSave(){
        return null;
    }
}
