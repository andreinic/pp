package ro.pricepage.json.dto;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import ro.pricepage.persistence.entities.ProductStore;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "product")
public class ProductDTO implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private Double price;
    
    public ProductDTO(){}
    
    public ProductDTO(Integer id, String name, List<ProductStore> allInstances){
    	this.id = id;
    	this.name = name;
    	this.price = null;
    	for(ProductStore inst : allInstances){
    		Double instPrice = inst.getPrice();
    		if((instPrice != null) && (this.price == null || this.price.doubleValue() > instPrice.doubleValue())){
    			this.price = Double.valueOf(instPrice.doubleValue());
    		}
    	}
    }

    @XmlElement(name = "id")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement(name = "name")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
