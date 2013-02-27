package ro.pricepage.rest;

import ro.pricepage.json.dto.StoreTypeDTO;
import ro.pricepage.persistence.entities.StoreType;
import ro.pricepage.service.StoresService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

@Stateless
@Path("/store-chain")
public class ShopsController
{
    @Inject
    private StoresService storesService;

    @Path("/{storeChainId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("shopChainId") int chainId){
        try{
            return Response.status(Response.Status.OK).build();
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @Path("/stores/types")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getShopTypes(){
        try{
            List<StoreType> types = storesService.findAllStoreTypes();
            if(types.isEmpty()){
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            List<StoreTypeDTO> ret = new LinkedList<>();
            for(StoreType st : types){
                StoreTypeDTO dto = new StoreTypeDTO();
                dto.setId(st.getId());
                dto.setName(st.getName());
                ret.add(dto);
            }
            return Response.status(Response.Status.OK).entity(new GenericEntity<>(ret, List.class)).build();
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
