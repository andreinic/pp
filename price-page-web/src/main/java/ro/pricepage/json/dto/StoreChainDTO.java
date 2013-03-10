package ro.pricepage.json.dto;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "storeChain")
public class StoreChainDTO implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String description;
    private List<ProductDTO> topProducts;
    private List<StoreDTO> stores;

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

    @XmlElement(name = "description")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement(name = "topProducts")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public List<ProductDTO> getTopProducts() {
        return topProducts;
    }

    public void setTopProducts(List<ProductDTO> topProducts) {
        this.topProducts = topProducts;
    }

    @XmlElementWrapper(name = "stores")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public List<StoreDTO> getStores() {
        return stores;
    }

    public void setStores(List<StoreDTO> stores) {
        this.stores = stores;
    }
}
