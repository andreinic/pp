package ro.pricepage.persistence.entities;

import javax.persistence.*;

/**
 * User: radutoev
 * Date: 30.11.2012
 * Time: 22:58
 */
@Entity
@Table(name = "producers")
@NamedQueries(value = {
    @NamedQuery(name = Producer.GET_PRODUCERS, query = "FROM Producer"),
    @NamedQuery(name = Producer.GET_PRODUCER_BY_NAME, query = "FROM Producer WHERE name = :name")
})
public class Producer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    public static final String GET_PRODUCERS = "Producer.getProducers";
    public static final String GET_PRODUCER_BY_NAME = "Producer.getProducerByName";

    private Integer id;
    private String name;

    @Id
    @Column(name = "id", unique = true, nullable = false, length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
