package ro.pricepage.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="locations")
@NamedQueries(value={
		@NamedQuery(name=Location.Q_FIND_ALL_CITIES_BY_COUNTY, query="SELECT DISTINCT(city) FROM Location WHERE county = :county"),
		@NamedQuery(name=Location.Q_FIND_ALL_COUNTIES, query="SELECT DISTINCT(county) FROM Location"),
		@NamedQuery(name=Location.Q_FIND_ALL_LOCATIONS_BY_COUNTY, query="FROM Location WHERE county = :county")
})
public class Location extends BaseEntity {
	private static final long serialVersionUID = -2535910147758488039L;
	public static final String Q_FIND_ALL_CITIES_BY_COUNTY = "Location.findAllCitiesByCounty";
	public static final String Q_FIND_ALL_LOCATIONS_BY_COUNTY = "Location.findAllLocationsByCounty";
	public static final String Q_FIND_ALL_COUNTIES = "Location.findAllCounties";

	private Integer id;
    private String city;
    private String county;

	@Id
	@Column(name="id", unique=true, nullable=false, length = 11)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

    @Column(name = "city", nullable = false, length = 50)
    public String getCity(){
        return city;
    }
    public void setCity(String city){
        this.city = city;
    }

    @Column(name = "county", nullable = false, length = 50)
    public String getCounty(){
        return county;
    }
    public void setCounty(String county){
        this.county = county;
    }
}
