package ro.pricepage.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="product_categories")
public class ProductCategory extends BaseEntity {
	private static final long serialVersionUID = -3557158368102788220L;
	private Integer id;
	private ProductCategory parent;
	private String name;
	private String description;
	
	@Id
	@Column(name="id", nullable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="fk_parent")
	public ProductCategory getParent() {
		return parent;
	}
	public void setParent(ProductCategory parent) {
		this.parent = parent;
	}
	
	@Column(name="name", length=100)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="description", columnDefinition="text")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
