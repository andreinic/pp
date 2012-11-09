package ro.pricepage.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="localities")
public class Locality extends BaseEntity {
	private static final long serialVersionUID = -2535910147758488039L;

	private Integer id;
	private Integer siruta;
	private String name;
	private String environment;
	private String superior;
	private County county;
	
	@Id
	@Column(name="id", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="siruta")
	public Integer getSiruta() {
		return siruta;
	}
	public void setSiruta(Integer siruta) {
		this.siruta = siruta;
	}
	
	@Column(name="name", length=50)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="environment", length=20)
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	
	@Column(name="superior", length=50)
	public String getSuperior() {
		return superior;
	}
	public void setSuperior(String superior) {
		this.superior = superior;
	}
	
	@ManyToOne
	@JoinColumn(name="fk_county")
	public County getCounty() {
		return county;
	}
	public void setCounty(County county) {
		this.county = county;
	}
}
