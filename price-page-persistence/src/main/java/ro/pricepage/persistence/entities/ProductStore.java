package ro.pricepage.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

@Entity
@Table(name="products_stores")
@Indexed(index="products")
public class ProductStore extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Double price;
	private Double discount;
	private String url;
	private Product product;
	private Store store;
	
	@Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@DocumentId(name="id")
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name="price",nullable=false)
    @Field(name="price", store=org.hibernate.search.annotations.Store.YES, index=Index.NO)
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	@Column(name="discount")
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	
	@Column(name="url", columnDefinition="text")
	@Field(name="url", store=org.hibernate.search.annotations.Store.YES, index=Index.NO)
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="fk_product", nullable=false)
	@IndexedEmbedded
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="fk_store", nullable=false)
	@IndexedEmbedded
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
}
