package ro.pricepage.persistence.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "promos")
@NamedQueries(value = {
		@NamedQuery(name = Promo.Q_FIND_FOR_PRODUCT_STORE_ID, query = "FROM Promo WHERE productStore.id = :productStoreId ORDER BY id DESC"),
		@NamedQuery(name = Promo.Q_COUNT_FOR_PRODUCT_STORE_ID, query = "SELECT COUNT(id) FROM Promo WHERE productStore.id = :productStoreId"),
		@NamedQuery(name = Promo.Q_FIND_ACTIVE_FOR_PRODUCT_STORE_ID, query = "FROM Promo WHERE productStore.store.id = :storeId AND (startDate IS NULL OR startDate < CURRENT_TIMESTAMP) AND (endDate IS NULL OR endDate > CURRENT_TIMESTAMP)")
})
public class Promo extends BaseEntity {
	private static final long serialVersionUID = 4618468479889025438L;
	
	public static final String Q_FIND_FOR_PRODUCT_STORE_ID = "Promo.findForProductStore";
	public static final String Q_COUNT_FOR_PRODUCT_STORE_ID = "Promo.countForProductStore";
	public static final String Q_FIND_ACTIVE_FOR_PRODUCT_STORE_ID = "Promo.findActiveForProductStore";

	private Integer id;
	private Double price;
	private Date startDate;
	private Date endDate;
	private String description;
	private ProductStore productStore;
	
	@Id
	@Column(name = "id", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "price")
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	@Column(name = "startDate")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@Column(name = "endDate")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Column(name = "description", columnDefinition="text")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@ManyToOne
	@JoinColumn(name = "fk_price", nullable = false)
	public ProductStore getProductStore() {
		return productStore;
	}
	public void setProductStore(ProductStore productStore) {
		this.productStore = productStore;
	}
}
