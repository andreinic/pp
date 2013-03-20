package ro.pricepage.rest;

import ro.pricepage.json.dto.CategoryDTO;
import ro.pricepage.persistence.entities.ProductCategory;
import ro.pricepage.service.ProductCategoriesService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Stateless
@Path("/categories")
public class CategoriesController
{
    @Inject
    private ProductCategoriesService categoriesService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategories(){
        try{
            Set<ProductCategory> productCategories = categoriesService.hierarchy();
            if(productCategories.isEmpty()){
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            List<CategoryDTO> ret = new LinkedList<>();
            for(ProductCategory pc : productCategories){
                CategoryDTO c = new CategoryDTO();
                c.setId(pc.getId());
                c.setName(pc.getName());
                List<CategoryDTO> childRet = processCategories(pc);
                c.setChildren(childRet);
                ret.add(c);
            }
            if(ret.isEmpty()){
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            return Response.status(Response.Status.OK).entity(new GenericEntity<>(ret, List.class)).build();
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{categoryId}")
    public Response getCategory(@PathParam("categoryId") Integer categoryId){
        try{
            ProductCategory pc = categoriesService.getById(categoryId);
            CategoryDTO dto = new CategoryDTO();
            dto.setId(pc.getId());
            dto.setName(pc.getName());
            return Response.status(Response.Status.OK).entity(new GenericEntity<>(dto, CategoryDTO.class)).build();
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    private List<CategoryDTO> processCategories(ProductCategory category){
        Collection<ProductCategory> children = category.getChildren();
        List<CategoryDTO> ret = new LinkedList<>();
        if(children != null && !children.isEmpty()){
            for(ProductCategory pc : children){
                CategoryDTO c = new CategoryDTO();
                c.setId(pc.getId());
                c.setName(pc.getName());
                List<CategoryDTO> childRet = processCategories(pc);
                c.setChildren(childRet);
                ret.add(c);
            }
            return ret;
        } else {
            return null;
        }
    }
}
