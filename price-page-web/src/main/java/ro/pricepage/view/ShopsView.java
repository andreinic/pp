package ro.pricepage.view;

import com.ocpsoft.pretty.faces.annotation.URLMapping;

import javax.inject.Named;

/**
 * Created with IntelliJ IDEA.
 * User: toev
 * Date: 11/1/12
 * Time: 10:49 PM
 * To change this template use File | Settings | File Templates.
 */
@Named(value = "shopsView")
@URLMapping(id = "shopsView", pattern = "/admin/magazine", viewId = "/WEB-INF/view/admin/shops.jsf")
public class ShopsView
{
}
