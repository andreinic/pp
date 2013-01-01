package ro.pricepage.persistence.entities;

import javax.persistence.*;

@Entity
@Table(name = "products")
@NamedQueries({
    @NamedQuery(name = Product.GET_PRODUCTS, query = "FROM Product"),
    @NamedQuery(name = Product.COUNT_PRODUCTS, query = "SELECT COUNT(p.id) FROM Product AS p")
})
public class Product extends BaseEntity
{
    public static final long serialVersionUID = 1L;

    public static final String GET_PRODUCTS = "Product.getProducts";
    public static final String COUNT_PRODUCTS = "Product.countProducts";

    private Integer id;
    private ProductCategory category;
    private Producer producer;
    private String name;
    private String description;

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    @JoinColumn(name = "fk_category", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    public ProductCategory getCategory() {
        return category;
    }
    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    @JoinColumn(name = "fk_producer", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    public Producer getProducer() {
        return producer;
    }
    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString(){
        return super.toString() + "ID: " + id.toString() + " Name: " + name + " " + "Category: " + category.toString() + "Producer: " + producer.toString();
    }
}
