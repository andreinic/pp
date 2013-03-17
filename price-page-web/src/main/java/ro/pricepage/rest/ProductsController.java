package ro.pricepage.rest;

import ro.pricepage.json.dto.ProductDTO;
import ro.pricepage.json.dto.ProductDetailDTO;
import ro.pricepage.json.dto.StoreDTO;
import ro.pricepage.persistence.entities.Product;
import ro.pricepage.service.FileService;
import ro.pricepage.service.ProductsService;

import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

@Stateless
@Path("/products")
public class ProductsController
{
	@Inject
	private ProductsService productsService;

	@Inject
	private FileService fileService;

	//TODO Implement accordingly
	@Path("/promotions")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPromotions(@QueryParam("start") int start,
			@QueryParam("count") int count){
		try{
			List<Object[]> products = productsService.getAggregatedProducts(start, start + count);
			if(products.isEmpty()){
				return Response.status(Response.Status.NO_CONTENT).build();
			}
			List<ProductDTO> ret = new LinkedList<>();
			for(Object[] p : products){
				ProductDTO dto = new ProductDTO();
				dto.setId((Integer)p[0]);
				dto.setName(p[1].toString());
				dto.setPrice((Double)p[2]);
                try{
                    dto.setImagesPaths(fileService.getImagePathsForProduct(((Integer)p[0]).intValue()));
                    if(!dto.getImagesPaths().isEmpty()){
                       dto.setImageData((byte[]) getProductImage(dto.getId(), dto.getImagesPaths().get(0)).getEntity());
                    }
                } catch (Exception e){
                    dto.setImagesPaths(null);
                    dto.setImageData(null);
                }
				ret.add(dto);
			}
			GenericEntity<List<Product>> entity = new GenericEntity(ret, List.class);
			return Response.status(Response.Status.OK).entity(entity).build();
		}
		catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

    @Path("/{productId}/images/{imagePath}")
    @GET
    @Produces("image/png")
    public Response getProductImage(@PathParam("productId") Integer productId,
                                    @PathParam("imagePath") String imagePath){
        try{
            BufferedImage image = ImageIO.read(fileService.getFileContent(imagePath));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", bos);
            byte[] data = bos.toByteArray();
            return Response.ok(data).build();
        } catch (Exception e){
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @Path("/count")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response countForCateg(@QueryParam("categoryId") int categoryId){
        try{
           return Response.status(Status.OK).entity(new GenericEntity<>(productsService.countForCateg(categoryId), Integer.class)).build();
        } catch (Exception e){
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@QueryParam("categoryId") Integer categoryId,
                        @QueryParam("start") Integer start,
                        @QueryParam("count") Integer count){
		try{
			List<Object[]> products = productsService.getAggregatedProductsByCateg(categoryId, start, start + count);
            if(products.isEmpty()) return Response.status(Status.NO_CONTENT).build();
			return Response.status(Response.Status.OK).entity(new GenericEntity(buildFromProducts(products), List.class)).build();
		}
		catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByStoreType(@QueryParam("storeTypeId") int storeTypeId,
                                   @QueryParam("start") int start,
                                   @QueryParam("count") int count){
        try{
            List<Object[]> products = productsService.getAggregatedProductsByStoreType(storeTypeId, start, count);
            if(products.isEmpty()) return Response.status(Status.NO_CONTENT).build();
            return Response.status(Response.Status.OK).entity(new GenericEntity(buildFromProducts(products), List.class)).build();
        } catch (Exception e){
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    private List<ProductDTO> buildFromProducts(List<Object[]> products){
        List<ProductDTO> ret = new LinkedList<>();
        for(Object[] p : products){
            ProductDTO dto = new ProductDTO();
            dto.setId((Integer)p[0]);
            dto.setName(p[1].toString());
            dto.setPrice((Double)p[2]);
            try{
                dto.setImagesPaths(fileService.getImagePathsForProduct(dto.getId().intValue()));
            } catch (Exception e){
                dto.setImagesPaths(null);
            }
            ret.add(dto);
        }
        return ret;
    }

	@GET
	@Path("/{productId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("productId") int id){
		try{
			Product p = productsService.get(id);
			List<Object[]> details = productsService.getProductDetails(p.getId());

			ProductDetailDTO ret = new ProductDetailDTO();
			ret.setId(p.getId());
			ret.setName(p.getName());
			ret.setDescription(p.getDescription());
			if(details != null){
				List<StoreDTO> stores = new LinkedList<>();
				for(Object[] o : details){
					StoreDTO s = new StoreDTO();
					s.setId((Integer)o[0]);
					s.setName(o[4].toString());
					s.setPrice((Double)o[5]);
					s.setAddress(o[3].toString());
					s.setLatitude((Double)o[2]);
					s.setLongitude((Double)o[1]);
					s.setZip(o[6].toString());
					stores.add(s);
				}
				ret.setStores(stores);
			}
			return Response.status(Response.Status.OK).entity(new GenericEntity(ret, ProductDetailDTO.class)).build();
		} catch (Exception e){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
