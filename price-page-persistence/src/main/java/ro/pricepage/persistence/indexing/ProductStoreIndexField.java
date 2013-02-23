package ro.pricepage.persistence.indexing;

import java.util.ArrayList;
import java.util.List;

public enum ProductStoreIndexField {
	CATEGORIES("categories", Boolean.TRUE),
	PRICE("price", Boolean.FALSE),
	PRODUCT_DESCRIPTION("product.description", Boolean.TRUE),
	PRODUCT_ID("product.id", Boolean.TRUE),
	PRODUCT_NAME("product.name", Boolean.TRUE),
	PRODUCT_PRODUCER_NAME("product.producer.name", Boolean.TRUE),
	STORE_CHAIN_NAME("store.chain.name", Boolean.TRUE),
	STORE_LOCALITY_CITY("store.locality.city", Boolean.TRUE),
	STORE_LOCALITY_COUNTY("store.locality.county", Boolean.TRUE),
	STORE_NAME("store.name", Boolean.TRUE),
	URL("url", Boolean.FALSE);
	
	private ProductStoreIndexField(String path, Boolean indexed){
		this.path = path;
		this.indexed = indexed;
	}
	
	private Boolean indexed;
	private String path;
	
	public static String[] getAllIndexedFieldPaths(){
		List<String> aux = new ArrayList<String>();
		for(ProductStoreIndexField field : ProductStoreIndexField.values()){
			if(field.indexed != null && field.indexed.booleanValue()){
				aux.add(field.path);
			}
		}
		
		return aux.toArray(new String[]{});
	}
	
	public String getPath(){
		return path;
	}
}
