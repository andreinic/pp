package ro.pricepage.rest;

import java.io.IOException;
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

import ro.pricepage.json.dto.SearchHitDTO;
import ro.pricepage.service.SearchService;

@Stateless
@Path("/search")
public class SearchController {

	@Inject
	private SearchService searchService;
	
	@Path("/text")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response search(@QueryParam("q") String text,
			@QueryParam("first") int first,
			@QueryParam("last") int last) throws ParseException, IOException{
		List<Document> docs = searchService.fullTextSearch(text, first, last);
		List<SearchHitDTO> dtos = SearchHitDTO.fromDocumentList(docs);
		GenericEntity<List<SearchHitDTO>> entity = new GenericEntity<List<SearchHitDTO>>(dtos, List.class);
		return Response.status(Status.OK).entity(entity).build();
	}
	
	@Path("/rebuild")
	@GET
	public Response rebuildIndex() throws InterruptedException{
		searchService.rebuildProductIndex();
		return Response.status(Status.OK).build();
	}
}
