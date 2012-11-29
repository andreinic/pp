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
@Table(name="stores")
public class Store extends BaseEntity {
	private static final long serialVersionUID = -316251772567024056L;
	private Integer id;
	private String zip;
	private String details;
	private Double longitude;
	private Double latitude;
	private StoreType storeType;
	private String contact;
	private StoreChain chain;
	private String address;
	private String url;
	private Location locality;
	private String name;
	
	public Store(){}
	
	public Store(String name, StoreChain chain, StoreType type, Location locality, String address, String zip, Double longitude, Double latitude, String url){
		this.name = name;
		this.chain = chain;
		this.storeType = type;
		this.locality = locality;
		this.address = address;
		this.zip = zip;
		this.url = url;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	@Id
	@Column(name="id", nullable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="zip", length=20)
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	@Column(name="details", columnDefinition="text")
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	@Column(name="longitude", length=14, precision=2)
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	@Column(name="latitude", length=14, precision=2)
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	@ManyToOne
	@JoinColumn(name="fk_storeType")
	public StoreType getStoreType() {
		return storeType;
	}
	public void setStoreType(StoreType storeType) {
		this.storeType = storeType;
	}
	
	@Column(name="contact", columnDefinition="text")
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	@ManyToOne
	@JoinColumn(name="fk_chain")
	public StoreChain getChain() {
		return chain;
	}
	public void setChain(StoreChain chain) {
		this.chain = chain;
	}
	
	@Column(name="address", columnDefinition="text")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name="url", columnDefinition="text")
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@ManyToOne
	@JoinColumn(name="fk_location")
	public Location getLocality() {
		return locality;
	}
	public void setLocality(Location locality) {
		this.locality = locality;
	}

	@Column(name="name", length=100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
