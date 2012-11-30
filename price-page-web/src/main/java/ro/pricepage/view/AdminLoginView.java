package ro.pricepage.view;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLQueryParameter;
import org.hephaestus.db.encryption.SHA256;
import ro.pricepage.persistence.entities.User;
import ro.pricepage.service.UserService;
import ro.pricepage.utils.SessionObject;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import static ro.pricepage.utils.CookieUtils.COOKIE_AGE;
import static ro.pricepage.utils.CookieUtils.addCookie;
import static ro.pricepage.utils.CookieUtils.removeCookie;

/**
 * User: radutoev
 * Date: 11.11.2012
 * Time: 19:30
 */
@Named(value = "adminLoginView")
@Stateless
@RequestScoped
@URLMapping(id = "adminLoginView", pattern = "/admin/login", viewId = "/WEB-INF/view/admin/adminLogin.jsf")
public class AdminLoginView implements Serializable
{
    private static final long serialVersionUID = 1L;

    @EJB
    private UserService userService;

    @URLQueryParameter(value = "url", mappingId = "adminLoginView")
    private String url;

    private String username;
    private String password;
    private boolean remember;

    public String login() throws NoSuchAlgorithmException, UnsupportedEncodingException{
        System.out.println(url);
        String hashedPass = SHA256.hash(password);
        User user = userService.getUser(username, hashedPass);
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
        if(user != null){
            SessionObject so = new SessionObject();
            so.setName(user.getUsername());
            request.getSession().setAttribute("user", so);
            final String cookieName = SHA256.hash(so.toString()) + request.getRemoteAddr();
            if(remember){
                addCookie(response, cookieName, UUID.randomUUID().toString(), COOKIE_AGE);
            } else {
                removeCookie(response, cookieName);
            }
            if(url != null && !url.equals("")){
                try{
                    response.sendRedirect(url);
                } catch (IOException e){
                    throw new IllegalStateException(e);
                }
                fc.responseComplete();
            } else {
                return "pretty:categoriesView"; //TODO should be home page of admin view.
            }
        } else {
            //TODO Display wrong user/pass.
        }
        return null;
    }

    public String getUrl(){ return url; }
    public void setUrl(String value){ url = value; }

    public String getUsername(){ return username; }
    public void setUsername(String value){ username = value; }

    public String getPassword(){ return password; }
    public void setPassword(String value){ password = value; }

    public boolean isRemember(){ return remember; }
    public void setRemember(boolean value){ remember = value; }
}
