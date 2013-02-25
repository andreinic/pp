package ro.pricepage.rest;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("/shop-chain")
public class ShopsController
{

    @Path("/{shopChainId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("shopChainId") int chainId){
        try{
            return Response.status(Response.Status.OK).build();
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
