package ro.pricepage.view;

import com.ocpsoft.pretty.faces.annotation.URLMapping;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: toev
 * Date: 11/1/12
 * Time: 10:49 PM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean(name = "shopsView")
@URLMapping(id = "shopsView", pattern = "/admin/magazine", viewId = "/WEB-INF/view/admin/shops.jsf")
public class ShopsView implements Serializable
{
    private static final long serialVersionUID = 1L;


}
