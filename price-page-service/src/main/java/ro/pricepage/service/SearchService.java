package ro.pricepage.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser.Operator;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;
import org.hibernate.search.MassIndexer;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

import ro.pricepage.persistence.entities.ProductStore;
import ro.pricepage.persistence.indexing.Field;
import ro.pricepage.qualifiers.MySQLDatabase;

@Named(value = "searchService")
@Stateless
@Startup
public class SearchService extends BaseService {
	private static final long serialVersionUID = 1L;
	private static final String[] fields = Field.getAllIndexedFieldPaths();

	@Inject
	@MySQLDatabase
	private EntityManager em;

	public void rebuildProductIndex() throws InterruptedException{
		FullTextEntityManager fullTextEm = Search.getFullTextEntityManager(em);
		MassIndexer massIndexer = fullTextEm.createIndexer(ProductStore.class);
		massIndexer.purgeAllOnStart(true).startAndWait();
		fullTextEm.flushToIndexes();
	}
	
	/**
	 * @param text query text
	 * @param first 0-based, including
	 * @param last 0-based, including
	 * @throws ParseException
	 * @throws IOException
	 */
	public List<Document> fullTextSearch(String text, int first, int last) throws ParseException, IOException{
		FullTextEntityManager fullTextEm = Search.getFullTextEntityManager(em);
		IndexReader reader = fullTextEm.getSearchFactory().getIndexReaderAccessor().open(ProductStore.class);
		IndexSearcher searcher = new IndexSearcher(reader);
		
		MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_35, fields, new StandardAnalyzer(Version.LUCENE_35));
		parser.setDefaultOperator(Operator.OR);
		Query query = parser.parse(text);
		TopDocs topDocs = searcher.search(query, 20);
		ScoreDoc[] hits = topDocs.scoreDocs;
		
		List<Document> docs = new ArrayList<Document>();
		for(int i = first;i <= last && i < hits.length;i++){
			Document doc = searcher.doc(hits[i].doc);
			docs.add(doc);
		}
		
		return docs;
	}
}
