package ro.pricepage.rest;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ro.pricepage.json.dto.PromoDTO;
import ro.pricepage.persistence.entities.Promo;
import ro.pricepage.service.PromosService;

@Stateless
@Path("/promos")
public class PromosController {
	@Inject
	private PromosService promosService;
	
	@GET
	@Path("/store/{id}/current")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCurrentPromotionsForStore(@PathParam(value = "id") int id){
		try{
			List<Promo> promos = promosService.getCurrentPromosForStore(Integer.valueOf(id));
			if(promos.isEmpty()){
				return Response.status(Response.Status.NO_CONTENT).build();
			}
			List<PromoDTO> retList = new LinkedList<>();
			for(Promo promo : promos){
				PromoDTO dto = new PromoDTO(promo);
				retList.add(dto);
			}
			GenericEntity<List<PromoDTO>> entity = new GenericEntity<List<PromoDTO>>(retList, List.class);
			return Response.status(Response.Status.OK).entity(entity).build();
		}
		catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
