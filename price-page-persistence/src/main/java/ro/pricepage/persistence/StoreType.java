package ro.pricepage.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="store_types")
public class StoreType extends BaseEntity {
	private static final long serialVersionUID = -8987519479616465542L;
	private Integer id;
	private String name;
	
	@Id
	@Column(name="id", nullable=false, unique=true)
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
