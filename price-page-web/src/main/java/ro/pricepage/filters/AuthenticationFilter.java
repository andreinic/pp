package ro.pricepage.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

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

        String uri = request.getRequestURI();
        boolean authenticated = true;

        if(authenticated){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        //redirect to login page
        response.sendRedirect(request.getContextPath() + "/admin/login?url="+uri);
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
