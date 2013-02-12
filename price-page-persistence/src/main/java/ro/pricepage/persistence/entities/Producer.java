package ro.pricepage.persistence.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;

/**
 * User: radutoev
 * Date: 30.11.2012
 * Time: 22:58
 */
@Entity
@Table(name = "producers")
@NamedQueries(value = {
    @NamedQuery(name = Producer.GET_PRODUCERS, query = "FROM Producer"),
    @NamedQuery(name = Producer.GET_PRODUCER_BY_NAME, query = "FROM Producer WHERE name = :name"),
    @NamedQuery(name = Producer.GET_PRODUCER_BY_ID, query = "FROM Producer WHERE id = :id")
})
@Analyzer(definition = "ngram")
public class Producer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    public static final String GET_PRODUCERS = "Producer.getProducers";
    public static final String GET_PRODUCER_BY_NAME = "Producer.getProducerByName";
    public static final String GET_PRODUCER_BY_ID = "Producer.getProducerById";

    private Integer id;
    private String name;
    private Collection<Product> products;

    @Id
    @Column(name = "id", unique = true, nullable = false, length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false, length = 50)
    @Field(name="name", store=org.hibernate.search.annotations.Store.YES, analyze=Analyze.YES, index=Index.YES)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy="producer")
    @ContainedIn
    public Collection<Product> getProducts() {
		return products;
	}
	public void setProducts(Collection<Product> products) {
		this.products = products;
	}
	@Override
    public String toString(){
        return super.toString() + "ID: " + id.toString() + " Name: " + name;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null) return false;
        if(obj == this) return true;
        if(obj.getClass() != getClass()) return false;
        Producer producer = (Producer) obj;
        return producer.getId() == id && producer.getName().equals(name);
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        return prime * result + ((name != null) ? name.hashCode() : 0);
    }
}
