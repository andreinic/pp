package ro.pricepage.json.dto;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.lang.Double;
import java.lang.Integer;
import java.lang.String;

@XmlRootElement(name = "store")
public class StoreDTO implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String address;
    private String zip;
    private Double latitude;
    private Double longitude;
    private Double price;

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

    @XmlElement(name = "address")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public String getAddress() {
        return address;
    }

    @XmlElement(name = "zip")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @XmlElement(name = "latitude")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @XmlElement(name = "longitude")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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
