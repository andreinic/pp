package ro.pricepage.rest;

import ro.pricepage.persistence.entities.Product;
import ro.pricepage.service.ProductsService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless
@Path("/products")
public class ProductsController
{

    @Inject
    private ProductsService productsService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@QueryParam("start") int start,
                        @QueryParam("count") int count){
        GenericEntity<List<Product>> entity = new GenericEntity<List<Product>>(productsService.list(start, count), List.class);
        return Response.status(200).entity(entity).build();
    }


}
