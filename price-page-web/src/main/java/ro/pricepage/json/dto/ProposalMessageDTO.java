package ro.pricepage.json.dto;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "proposal_message")
public class ProposalMessageDTO
{
    private String storeName;
    private String storeType;
    private String address;
    private String phone;
    private String category;

    @XmlElement(name = "store_name")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    @XmlElement(name = "store_type")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    @XmlElement(name = "address")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @XmlElement(name = "phone")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @XmlElement(name = "category")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
