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

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

import ro.pricepage.persistence.indexing.CategoryBridge;

@Entity
@Table(name = "products")
@Indexed(index = "products")
@NamedQueries({
    @NamedQuery(name = Product.GET_PRODUCT_BY_ID, query = "FROM Product AS p WHERE p.id = :productId"),
    @NamedQuery(name = Product.GET_PRODUCTS, query = "FROM Product"),
    @NamedQuery(name = Product.COUNT_PRODUCTS, query = "SELECT COUNT(p.id) FROM Product AS p"),
    @NamedQuery(name = Product.COUNT_PRODUCTS_FOR_CATEGORY, query = "SELECT COUNT(p.id) FROM Product AS p WHERE p.category.id = :catId"),
    @NamedQuery(name = Product.GET_PRODUCTS_FOR_PARENT_CATEGORY, query = "FROM Product AS p WHERE p.category.id IN (SELECT pc.id FROM ProductCategory AS pc WHERE pc.parent.id = :parentId)")
})
@Analyzer(definition = "ngram")
public class Product extends BaseEntity
{
    public static final long serialVersionUID = 1L;

    public static final String GET_PRODUCT_BY_ID = "Product.getProductById";
    public static final String GET_PRODUCTS = "Product.getProducts";
    public static final String GET_PRODUCTS_FOR_PARENT_CATEGORY = "Product.getProductsForParentCategory";
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
    @DocumentId(name = "id")
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    @JoinColumn(name = "fk_category", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @Field(bridge=@FieldBridge(impl=CategoryBridge.class))
    public ProductCategory getCategory() {
        return category;
    }
    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    @JoinColumn(name = "fk_producer", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @IndexedEmbedded
    public Producer getProducer() {
        return producer;
    }
    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    @Column(name = "name", nullable = false)
    @Field(name="name", store=Store.YES, analyze=Analyze.YES, index=Index.YES)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    @Field(name="description", store=Store.YES, analyze=Analyze.YES, index=Index.YES)
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy = "product", cascade=CascadeType.ALL)
    @ContainedIn
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
