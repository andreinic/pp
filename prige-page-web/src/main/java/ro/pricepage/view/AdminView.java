package ro.pricepage.view;

import com.ocpsoft.pretty.faces.annotation.URLMapping;

import javax.inject.Named;

/**
 * User: radutoev
 * Date: 27.10.2012
 * Time: 16:22
 */
@Named(value = "adminView")
@URLMapping(id = "adminView", pattern = "/admin", viewId = "/WEB-INF/view/admin/main.jsf")
public class AdminView
{

}
