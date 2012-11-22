package ro.pricepage;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import java.io.Serializable;

/**
 * User: radutoev                                                                                                         l
 * Date: 20.11.2012
 * Time: 23:17
 */
@Named(value = "webResource")
@SessionScoped
public class WebResource implements Serializable
{
    //so as to make it capable for passivation.
    private static final long serialVersionUID = 1L;

    @Produces @PricePageDefault @Resource
    private SessionContext sessionContext;
}
