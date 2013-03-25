package ro.pricepage.json.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import ro.pricepage.persistence.entities.Promo;

@XmlRootElement(name = "promo")
public class PromoDTO implements Serializable {
	private static final long serialVersionUID = -6350128077664557562L;
	
	private Date startDate;
	private Date endDate;
	private Double price;
	
	public PromoDTO(Promo promo){
		this.startDate = promo.getStartDate();
		this.endDate = promo.getEndDate();
		this.price = promo.getPrice();
	}
	
	@XmlElement(name = "startDate")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@XmlElement(name = "endDate")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@XmlElement(name = "price")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
}
