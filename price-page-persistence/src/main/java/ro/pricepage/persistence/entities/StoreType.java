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
@Table(name="store_types")
@NamedQueries(value={
		@NamedQuery(name=StoreType.Q_FIND_ALL_NAMES, query="SELECT DISTINCT(name) FROM StoreType"),
		@NamedQuery(name=StoreType.Q_FIND_BY_NAME, query="FROM StoreType WHERE name = :name")
})
public class StoreType extends BaseEntity {
	private static final long serialVersionUID = -8987519479616465542L;
	public static final String Q_FIND_ALL_NAMES = "StoreType.findAllNames";
	public static final String Q_FIND_BY_NAME = "StoreType.findByName";
	
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
