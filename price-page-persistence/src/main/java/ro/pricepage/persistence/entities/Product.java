package ro.pricepage.persistence.entities;

import java.util.Set;

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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

import ro.pricepage.persistence.indexing.CategoryBridge;

@Entity
@Table(name = "products")
@NamedQueries({
    @NamedQuery(name = Product.GET_PRODUCTS, query = "FROM Product"),
    @NamedQuery(name = Product.COUNT_PRODUCTS, query = "SELECT COUNT(p.id) FROM Product AS p"),
    @NamedQuery(name = Product.COUNT_PRODUCTS_FOR_CATEGORY, query = "SELECT COUNT(p.id) FROM Product AS p WHERE p.category.id = :catId")
})
@Analyzer(definition = "ngram")
@XmlRootElement(name = "product")
public class Product extends BaseEntity
{
    public static final long serialVersionUID = 1L;

    public static final String GET_PRODUCTS = "Product.getProducts";
    public static final String COUNT_PRODUCTS = "Product.countProducts";
    public static final String COUNT_PRODUCTS_FOR_CATEGORY = "Product.countProductsForCategory";

    private Integer id;
    private ProductCategory category;
    private Producer producer;
    private String name;
    private String description;
    private Set<ProductStore> storeInstances;

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlElement(name = "id")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    @JoinColumn(name = "fk_category", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @Field(bridge=@FieldBridge(impl=CategoryBridge.class))
    @JsonIgnore
    public ProductCategory getCategory() {
        return category;
    }
    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    @JoinColumn(name = "fk_producer", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @IndexedEmbedded
    @JsonIgnore
    public Producer getProducer() {
        return producer;
    }
    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    @Column(name = "name", nullable = false)
    @Field(name="name", store=Store.YES, analyze=Analyze.YES, index=Index.YES)
    @XmlElement(name = "id")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    @Field(name="description", store=Store.YES, analyze=Analyze.YES, index=Index.YES)
    @XmlElement(name = "description")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy = "product", cascade=CascadeType.ALL)
    @ContainedIn
    @JsonIgnore
    public Set<ProductStore> getStoreInstances() {
		return storeInstances;
	}
	public void setStoreInstances(Set<ProductStore> storeInstances) {
		this.storeInstances = storeInstances;
	}
	
	@Override
    public String toString(){
        return super.toString() + "ID: " + id.toString() + " Name: " + name + " " + "Category: " + category.toString() + "Producer: " + producer.toString();
    }
}
