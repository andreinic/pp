package ro.pricepage.producers;

import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.ServiceLoader;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.jcr.Repository;
import javax.jcr.RepositoryFactory;

import ro.pricepage.qualifiers.JcrRepository;

@Stateless
@Named("jcrRepositoryProducer")
public class JcrRepositoryProducer implements Serializable {
	private static final long serialVersionUID = -1680823547347936819L;

	@JcrRepository
	@Produces
	@ApplicationScoped
	public Repository createRepository() throws Exception{
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("/jcr-config.properties");
		Properties properties = new Properties();
		properties.load(is);
		is.close();
		Map<String, String> config = new HashMap<>();
		for(Object key : properties.keySet()){
			Object value = properties.get(key);
			config.put(key.toString(), value.toString());
		}
		Iterator<RepositoryFactory> it = ServiceLoader.load(RepositoryFactory.class).iterator();
		if(it.hasNext()){
			return it.next().getRepository(config);
		}
		throw new Exception("Could not create JCR repository");
	}
}
