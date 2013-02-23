package ro.pricepage.json.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.lucene.document.Document;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import ro.pricepage.persistence.indexing.ProductStoreIndexField;

@XmlRootElement(name="searchHitDTO")
public class SearchHitDTO {
	private String productName;
	private String storeName;
	
	public SearchHitDTO(Document d){
		this.productName = d.get(ProductStoreIndexField.PRODUCT_NAME.getPath());
		this.storeName = d.get(ProductStoreIndexField.STORE_NAME.getPath());
	}
	
	public static List<SearchHitDTO> fromDocumentList(List<Document> docs){
		List<SearchHitDTO> retList = new ArrayList<SearchHitDTO>();
		for(Document doc : docs){
			retList.add(new SearchHitDTO(doc));
		}
		return retList;
	}

	@XmlElement(name = "name")
	@JsonSerialize(include=Inclusion.NON_NULL)
	public String getProductName() {
		return productName;
	}

	@XmlElement(name = "store")
	@JsonSerialize(include=Inclusion.NON_NULL)
	public String getStoreName() {
		return storeName;
	}
}
