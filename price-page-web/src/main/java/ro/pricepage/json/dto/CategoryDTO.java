package ro.pricepage.json.dto;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "category")
public class CategoryDTO implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private List<CategoryDTO> children;

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

    public void setChildren(List<CategoryDTO> children) {
        this.children = children;
    }

    @XmlElementWrapper(name = "children")
    @XmlElement(name = "category")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public List<CategoryDTO> getChildren() {
        return children;
    }
}
