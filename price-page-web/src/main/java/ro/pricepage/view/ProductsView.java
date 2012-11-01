package ro.pricepage.view;

import com.ocpsoft.pretty.faces.annotation.URLMapping;

import javax.inject.Named;

/**
 * User: radutoev
 * Date: 27.10.2012
 * Time: 16:22
 */
@Named(value = "productsView")
@URLMapping(id = "productsView", pattern = "/admin/produse", viewId = "/WEB-INF/view/admin/products.jsf")
public class ProductsView
{

}
