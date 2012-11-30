package ro.pricepage.filters;

import org.hephaestus.db.encryption.SHA256;
import ro.pricepage.utils.SessionObject;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ro.pricepage.utils.CookieUtils.*;

/**
 * User: radutoev
 * Date: 23.11.2012
 * Time: 23:23
 */
public class AuthenticationFilter implements Filter
{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        SessionObject so = (SessionObject) request.getSession().getAttribute("user");
        String cookieName = null;
        try{
            cookieName = SHA256.hash(so.toString()) + request.getRemoteAddr();
        } catch (Exception e){ throw new ServletException("Exception hashing string"); }
        if(so != null){
            String uuid = getCookieValue(request, cookieName);
            if(uuid != null){
                addCookie(response, cookieName, uuid, COOKIE_AGE); //extend the cookie age
            } else {
                so = null;
                removeCookie(response, cookieName);
            }
        }
        String uri = request.getRequestURI();
        if(so == null){
            response.sendRedirect(request.getContextPath()+"/admin/login?url="+uri);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
