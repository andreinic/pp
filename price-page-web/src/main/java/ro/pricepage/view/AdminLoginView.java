package ro.pricepage.view;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import ro.pricepage.qualifiers.PricePageDefault;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * User: radutoev
 * Date: 11.11.2012
 * Time: 19:30
 */
@Named(value = "adminLoginView")
@Stateless
@URLMapping(id = "adminLoginView", pattern = "/admin/login", viewId = "/WEB-INF/view/admin/adminLogin.jsf")
public class AdminLoginView implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Inject @PricePageDefault
    private SessionContext sessionContext;

    public String login(){
        System.out.println(sessionContext.getCallerPrincipal().getName());
        return "pretty:categoriesView";
    }
}
