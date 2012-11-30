package ro.pricepage.service;

import ro.pricepage.persistence.entities.User;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * User: radutoev
 * Date: 29.11.2012
 * Time: 22:30
 */
@Named("userService")
@Stateless
public class UserService extends BaseService
{
    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;
    
    public User getUser(String username, String password){
        try{
            return em.createNamedQuery(User.GET_USER_BY_NAME_AND_PASSWORD, User.class).getSingleResult();
        } catch (Exception e){
            return null;
        }
    }
}
