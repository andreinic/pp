package ro.pricepage.persistence.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

@Entity
@Table(name="product_categories")
@NamedQueries(value = {
    @NamedQuery(name = ProductCategory.GET_CATEGORY_BY_ID, query="FROM ProductCategory AS pc WHERE pc.id = :id"),
    @NamedQuery(name = ProductCategory.GET_CATEGORY_HIERARCHY, query = "FROM ProductCategory AS pc LEFT JOIN FETCH pc.children WHERE pc.parent IS NULL ORDER BY pc.parent, pc.id"),
    @NamedQuery(name = ProductCategory.GET_CATEGORIES, query = "FROM ProductCategory AS pc WHERE pc.parent IS NOT NULL"),
    @NamedQuery(name = ProductCategory.GET_ALL_CATEGORIES, query = "FROM ProductCategory ORDER BY name ASC")
})
public class ProductCategory extends BaseEntity {
	private static final long serialVersionUID = -3557158368102788220L;

    public static final String GET_CATEGORY_BY_ID = "ProductCategory.getCategoryById";
    public static final String GET_CATEGORY_HIERARCHY = "ProductCategory.getCategoryHierarchy";
    public static final String GET_CATEGORIES = "ProductCategory.getCategories";
    public static final String GET_ALL_CATEGORIES = "ProductCategory.getAllCategories";

	private Integer id;
	private ProductCategory parent;
    private Collection<ProductCategory> children = new ArrayList<>();
	private String name;
	private String description;
    private Integer lft;
    private Integer rgt;

	@Id
	@Column(name="id", nullable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name="fk_parent", nullable = true)
	public ProductCategory getParent() {
		return parent;
	}
	public void setParent(ProductCategory parent) {
		this.parent = parent;
	}

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy="parent")
    @OrderColumn(name = "fk_parent")
    public Collection<ProductCategory> getChildren(){
        return children;
    }
    public void setChildren(Collection<ProductCategory> value){
        children = value;
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

    @Column(name="lft", nullable=false)
    public Integer getLft() {
        return lft;
    }
    public void setLft(Integer lft) {
        this.lft = lft;
    }

    @Column(name="rgt", nullable=false)
    public Integer getRgt() {
        return rgt;
    }
    public void setRgt(Integer rgt) {
        this.rgt = rgt;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null) return false;
        if(this == obj) return true;
        if(obj.getClass() != this.getClass()) return false;
        ProductCategory pc = (ProductCategory) obj;
        return name.equals(pc.getName());
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        return prime * result + ((name != null) ? name.hashCode() : 0);
    }
}
