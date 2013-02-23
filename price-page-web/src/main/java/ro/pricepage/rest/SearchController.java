package ro.pricepage.rest;

import java.io.IOException;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
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

import ro.pricepage.json.dto.SearchHitDTO;
import ro.pricepage.service.SearchService;

@Stateful
@SessionScoped
@Path("/search")
public class SearchController {

	@Inject
	private SearchService searchService;
	
	@Path("/text")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response search(@QueryParam("q") String text,
			@QueryParam("start") int first,
			@QueryParam("count") int last) throws ParseException, IOException{
        try{
            List<Document> docs = searchService.fullTextSearch(text, first, last);
            
            //TODO: handle product documents instead of product-store documents; perform needed aggregations
            List<SearchHitDTO> dtos = SearchHitDTO.fromDocumentList(docs);
            if(dtos.isEmpty()){
                return Response.status(Status.NO_CONTENT).build();
            }
            GenericEntity<List<SearchHitDTO>> entity = new GenericEntity<List<SearchHitDTO>>(dtos, List.class);
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
