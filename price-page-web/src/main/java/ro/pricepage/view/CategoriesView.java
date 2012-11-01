package ro.pricepage.view;

import com.ocpsoft.pretty.faces.annotation.URLMapping;

import javax.inject.Named;

/**
 * Created with IntelliJ IDEA.
 * User: toev
 * Date: 11/1/12
 * Time: 10:24 PM
 * To change this template use File | Settings | File Templates.
 */
@Named(value = "categoriesView")
@URLMapping(id = "categoriesView", pattern = "/admin/categorii", viewId = "/WEB-INF/view/admin/categories.jsf")
public class CategoriesView
{

}
