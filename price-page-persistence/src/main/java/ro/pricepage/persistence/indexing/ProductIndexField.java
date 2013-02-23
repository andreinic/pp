package ro.pricepage.persistence.indexing;

import java.util.ArrayList;
import java.util.List;

public enum ProductIndexField {
	CATEGORIES("categories", Boolean.TRUE),
	DESCRIPTION("description", Boolean.TRUE),
	ID("id", Boolean.TRUE),
	NAME("name", Boolean.TRUE),
	PRODUCER_NAME("producer.name", Boolean.TRUE);
	
	private ProductIndexField(String path, Boolean indexed){
		this.path = path;
		this.indexed = indexed;
	}
	
	private Boolean indexed;
	private String path;
	
	public static String[] getAllIndexedFieldPaths(){
		List<String> aux = new ArrayList<String>();
		for(ProductIndexField field : ProductIndexField.values()){
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
