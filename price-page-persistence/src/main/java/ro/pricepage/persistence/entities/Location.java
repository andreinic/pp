package ro.pricepage.persistence.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;

@Entity
@Table(name="locations")
@NamedQueries(value={
		@NamedQuery(name=Location.Q_FIND_ALL_CITIES_BY_COUNTY, query="SELECT DISTINCT(city) FROM Location WHERE county = :county"),
		@NamedQuery(name=Location.Q_FIND_ALL_COUNTIES, query="SELECT DISTINCT(county) FROM Location"),
		@NamedQuery(name=Location.Q_FIND_ALL_LOCATIONS_BY_COUNTY, query="FROM Location WHERE county = :county"),
		@NamedQuery(name=Location.Q_FIND_ALL_CITIES_WITH_STORES, query="SELECT DISTINCT(city) FROM Location l WHERE EXISTS(SELECT s FROM Store s WHERE s.locality = l)")
})
@Analyzer(definition = "ngram")
public class Location extends BaseEntity {
	private static final long serialVersionUID = -2535910147758488039L;
	public static final String Q_FIND_ALL_CITIES_BY_COUNTY = "Location.findAllCitiesByCounty";
	public static final String Q_FIND_ALL_LOCATIONS_BY_COUNTY = "Location.findAllLocationsByCounty";
	public static final String Q_FIND_ALL_COUNTIES = "Location.findAllCounties";
	public static final String Q_FIND_ALL_CITIES_WITH_STORES = "Location.findAllCitiesWithStores";

	private Integer id;
    private String city;
    private String county;
    private Collection<Store> stores;

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
    @Field(name="city", store=org.hibernate.search.annotations.Store.YES, analyze=Analyze.YES, index=Index.YES)
    public String getCity(){
        return city;
    }
    public void setCity(String city){
        this.city = city;
    }

    @Column(name = "county", nullable = false, length = 50)
    @Field(name="county", store=org.hibernate.search.annotations.Store.YES, analyze=Analyze.YES, index=Index.YES)
    public String getCounty(){
        return county;
    }
    public void setCounty(String county){
        this.county = county;
    }
    
    @OneToMany(mappedBy="locality")
    @ContainedIn
	public Collection<Store> getStores() {
		return stores;
	}
	public void setStores(Collection<Store> stores) {
		this.stores = stores;
	}
}
