package ro.pricepage.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="store_chains")
@NamedQueries(value={
		@NamedQuery(name=StoreChain.Q_FIND_ALL, query="FROM StoreChain"),
		@NamedQuery(name=StoreChain.Q_FIND_ALL_NAMES, query="SELECT DISTINCT(name) FROM StoreChain"),
		@NamedQuery(name=StoreChain.Q_FIND_BY_NAME, query="FROM StoreChain WHERE name = :name"),
		@NamedQuery(name=StoreChain.Q_FIND_ALL_NAMES_IN_CITY, query="SELECT DISTINCT(name) FROM StoreChain chain WHERE EXISTS (SELECT s FROM Store s WHERE s.chain = chain AND s.locality.city = :city)")
})
public class StoreChain extends BaseEntity {
	private static final long serialVersionUID = 887558032395422628L;
	public static final String Q_FIND_ALL = "StoreChain.findAll";
	public static final String Q_FIND_ALL_NAMES = "StoreChain.findAllNames";
	public static final String Q_FIND_BY_NAME = "StoreChain.findByName";
	public static final String Q_FIND_ALL_NAMES_IN_CITY = "StoreChain.findAllNamesInCity";
	
	private Integer id;
	private String name;
	
	@Id
	@Column(name="id", nullable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="name", length=50)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
