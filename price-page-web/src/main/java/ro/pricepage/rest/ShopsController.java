package ro.pricepage.rest;

import ro.pricepage.json.dto.ProductDTO;
import ro.pricepage.json.dto.StoreChainDTO;
import ro.pricepage.json.dto.StoreDTO;
import ro.pricepage.json.dto.StoreTypeDTO;
import ro.pricepage.persistence.entities.Store;
import ro.pricepage.persistence.entities.StoreChain;
import ro.pricepage.persistence.entities.StoreType;
import ro.pricepage.service.ProductsService;
import ro.pricepage.service.StoresService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Stateless
@Path("/store-chain")
public class ShopsController
{
    @Inject
    private StoresService storesService;

    @Inject
    private ProductsService productsService;

    @Path("/stores/{shopId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("shopId") int shopId,
                        @QueryParam("start") int start,
                        @QueryParam("cout") int count){
        try{
            StoreChain sc = storesService.findParentStoreChain(shopId);
            Collection<Store> stores = sc.getStores();
            List<Object[]> products = productsService.getAggregatedProductsByStoreChain(sc.getId(), start, start+count);

            StoreChainDTO dto = new StoreChainDTO();
            dto.setId(sc.getId());
            dto.setName(sc.getName());

            List<StoreDTO> sdtos = new LinkedList<>();
            if(stores != null){
                for(Store store : stores){
                    StoreDTO sdto = new StoreDTO();
                    sdto.setId(sc.getId());
                    sdto.setLongitude(store.getLongitude());
                    sdto.setLatitude(store.getLatitude());
                    sdtos.add(sdto);
                }
            }
            dto.setStores(sdtos);

            List<ProductDTO> pdtos = new LinkedList<>();
            if(products != null){
                for(Object[] p : products){
                    ProductDTO pdto = new ProductDTO();
                    pdto.setId((Integer)p[0]);
                    pdto.setName(p[1].toString());
                    pdto.setPrice((Double)p[2]);
//				dto.setImagesPaths(fileService.getImagePathsForProduct(((Integer)p[0]).intValue()));
                    pdtos.add(pdto);
                }
            }
            dto.setTopProducts(pdtos);
            return Response.status(Response.Status.OK).entity(new GenericEntity<>(dto, StoreChainDTO.class)).build();
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
