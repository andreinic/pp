package ro.pricepage.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: radutoev
 * Date: 29.11.2012
 * Time: 23:30
 */
public class CookieUtils
{
    public static final int COOKIE_AGE = 259200; //30days.

    public static String getCookieValue(HttpServletRequest request, String name){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(name.equals(cookie.getName())) return cookie.getValue();
            }
        }
        return null;
    }
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge){
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
    public static void removeCookie(HttpServletResponse response, String name){
        addCookie(response, name, null, 0);
    }
}
