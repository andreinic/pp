package ro.pricepage.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;

import ro.pricepage.json.dto.ProductDTO;
import ro.pricepage.persistence.entities.ProductStore;
import ro.pricepage.persistence.indexing.ProductIndexField;
import ro.pricepage.service.ProductsService;
import ro.pricepage.service.SearchService;

@Stateless
@Path("/search")
public class SearchController {

	@Inject
	private SearchService searchService;
	
	@Inject
	private ProductsService productsService;
	
	@Path("/text")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response search(@QueryParam("q") String text,
			@QueryParam("start") int start,
			@QueryParam("count") int count) throws ParseException, IOException{
        try{
            List<Document> docs = searchService.fullTextSearch(text, start, start + count);            
            List<ProductDTO> dtos = new ArrayList<ProductDTO>();
            for(Document doc : docs){
            	String stringId = doc.get(ProductIndexField.ID.getPath());
            	int id = Integer.parseInt(stringId);
            	List<ProductStore> instances = productsService.getAllInstancesForProduct(id);
            	ProductDTO dto = new ProductDTO(Integer.valueOf(id), doc.get(ProductIndexField.NAME.getPath()), instances);
            	dtos.add(dto);
            }
            GenericEntity<List<ProductDTO>> entity = new GenericEntity<List<ProductDTO>>(dtos, List.class);
            return Response.status(Status.OK).entity(entity).build();
        } catch (Exception e){
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
	}
	
	@Path("/rebuild")
	@GET
	public Response rebuildIndex() throws InterruptedException{
		searchService.rebuildProductIndex();
		return Response.status(Status.OK).build();
	}
}
