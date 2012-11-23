package ro.pricepage.producers;

import ro.pricepage.qualifiers.PricePageDefault;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import java.io.Serializable;

/**
 * User: radutoev
 * Date: 20.11.2012
 * Time: 23:17
 */
@Named("sessionContextProducer")
@Stateless
public class SessionContextProducer implements Serializable
{
    //so as to make it capable for passivation.
    private static final long serialVersionUID = 1L;

    @Resource @Produces @PricePageDefault
    private static SessionContext sessionContext;
}
