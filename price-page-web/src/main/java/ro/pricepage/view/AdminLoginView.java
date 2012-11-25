package ro.pricepage.view;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLQueryParameter;
import ro.pricepage.qualifiers.PricePageDefault;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @URLQueryParameter(value = "url", mappingId = "adminLoginView")
    private String url;

    public String login(){
        System.out.println(sessionContext.getCallerPrincipal().getName());
        if(url != null && !url.equals("")){
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            try{
                response.sendRedirect(url);
            } catch (IOException e){
                throw new IllegalStateException(e);
            }
            fc.responseComplete();
            return null;
        } else {
            return "pretty:categoriesView"; //TODO should be home page of admin view.
        }
    }
}
