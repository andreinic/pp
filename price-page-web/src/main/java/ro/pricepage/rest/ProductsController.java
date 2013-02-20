package ro.pricepage.rest;

import ro.pricepage.json.dto.ProductDTO;
import ro.pricepage.json.dto.ProductDetailDTO;
import ro.pricepage.persistence.entities.Product;
import ro.pricepage.service.ProductsService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
        List<Product> products = productsService.list(start, count);
        if(products.isEmpty()){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        List<ProductDTO> ret = new LinkedList<>();
        Random rnd = new Random();
        for(Product p : products){
            ProductDTO dto = new ProductDTO();
            dto.setId(p.getId());
            dto.setName(p.getName());
            dto.setPrice(rnd.nextFloat());
            ret.add(dto);
        }
        GenericEntity<List<Product>> entity = new GenericEntity(ret, List.class);
        return Response.status(200).entity(entity).build();
    }

    @GET
    @Path("/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("productId") int id){
        try{
            Product p = productsService.get(id);
            ProductDetailDTO ret = new ProductDetailDTO();
            ret.setId(p.getId());
            ret.setName(p.getName());
            ret.setDescription(p.getDescription());
            return Response.status(Response.Status.OK).entity(new GenericEntity(ret, ProductDetailDTO.class)).build();
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
