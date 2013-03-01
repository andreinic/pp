package ro.pricepage.json.dto;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "contact_message")
public class ContactMessageDTO
{
    private String name;
    private String email;
    private String phone;
    private String message;

    @XmlElement(name = "name")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "email")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElement(name = "phone")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @XmlElement(name = "message")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
