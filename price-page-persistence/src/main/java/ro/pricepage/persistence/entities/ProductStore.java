package ro.pricepage.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.solr.analysis.LowerCaseFilterFactory;
import org.apache.solr.analysis.NGramFilterFactory;
import org.apache.solr.analysis.StandardFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

@Entity
@Table(name = "products_stores")
@Indexed(index = "products_stores")
@AnalyzerDef(name = "ngram", tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class), filters = {
		@TokenFilterDef(factory = StandardFilterFactory.class),
		@TokenFilterDef(factory = LowerCaseFilterFactory.class),
		@TokenFilterDef(factory = NGramFilterFactory.class, params = {
				@Parameter(name = "minGramSize", value = "3"),
				@Parameter(name = "maxGramSize", value = "3") }) })
@Analyzer(definition = "ngram")
@NamedQueries(value = { @NamedQuery(name = ProductStore.FIND_ALL_FOR_PRODUCT_ID, query = "FROM ProductStore WHERE product.id = :id") })
public class ProductStore extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL_FOR_PRODUCT_ID = "ProductStore.findAllForProductId";

	private Integer id;
	private Double price;
	private Double discount;
	private String url;
	private Product product;
	private Store store;

	@Id
	@Column(name = "id", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@DocumentId(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "price", nullable = false)
	@Field(name = "price", store = org.hibernate.search.annotations.Store.YES, index = Index.NO)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "discount")
	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	@Column(name = "url", columnDefinition = "text")
	@Field(name = "url", store = org.hibernate.search.annotations.Store.YES, index = Index.NO)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_product", nullable = false)
	@IndexedEmbedded
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_store", nullable = false)
	@IndexedEmbedded
	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
}
