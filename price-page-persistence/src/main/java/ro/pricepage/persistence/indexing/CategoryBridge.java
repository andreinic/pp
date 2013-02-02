package ro.pricepage.persistence.indexing;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.hibernate.search.bridge.FieldBridge;
import org.hibernate.search.bridge.LuceneOptions;

import ro.pricepage.persistence.entities.ProductCategory;

public class CategoryBridge implements FieldBridge {

	private static final String CATEGORIES_FIELD = "categories";

	@Override
	public void set(String name, Object cat, Document doc, LuceneOptions luceneOptions) {
		ProductCategory category = (ProductCategory) cat;
		StringBuilder sb = new StringBuilder();
		while(category != null){
			String categoryName = category.getName();
			if(categoryName != null){
				sb.append(categoryName);
				sb.append(" ");
			}
			category = category.getParent();
		}
		if(sb.length() > 0){
			doc.add(new Field(CATEGORIES_FIELD, sb.toString(), Store.NO, Index.ANALYZED));
		}
	}

}
