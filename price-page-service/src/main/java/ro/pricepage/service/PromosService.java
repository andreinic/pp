package ro.pricepage.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import ro.pricepage.qualifiers.MySQLDatabase;

@Named(value = "promosService")
@Stateless
public class PromosService extends BaseService {
	private static final long serialVersionUID = -7740364377444357250L;
	
	@Inject
	@MySQLDatabase
	private EntityManager em;
}
