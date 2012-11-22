package ro.pricepage.view;

import com.ocpsoft.pretty.faces.annotation.URLMapping;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;

/**
 * User: radutoev
 * Date: 11.11.2012
 * Time: 19:30
 */
@ManagedBean(name = "adminLoginView")
@RequestScoped
@URLMapping(id = "adminLoginView", pattern = "/admin/login", viewId = "/WEB-INF/view/admin/adminLogin.jsf")
public class AdminLoginView implements Serializable
{
    private static final long serialVersionUID = 1L;

    public void login(){
//        if(ctx == null) System.out.println("Context is null");
//        else if(ctx.getCallerPrincipal() == null) System.out.println("caller principal is null");
//        else System.out.println(ctx.getCallerPrincipal().getName());
    }
}
