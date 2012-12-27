package ro.pricepage.converters;

import ro.pricepage.persistence.entities.ProductCategory;
import ro.pricepage.service.ProductCategoriesService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.Serializable;

@FacesConverter(forClass = ProductCategory.class, value = "productCategoryConverter")
public class ProductCategoryConverter implements Converter, Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * Responsible for conversion between the page and the backing bean
     */
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        try{
            //Note: EJB annotated injection would fail because the converter is in the faces context and it does not have access to the ejb context.
            //Also from the spec: "allow the container to inject references to container managed resources into a managed bean instance before it is made accessible to the JSF application. Only beans declared to be in request, session, or application scope are eligble for resource injection."
            Context ctx = new InitialContext();
            ProductCategoriesService service = (ProductCategoriesService) ctx.lookup("java:global/price-page/ProductCategoriesService");
            assert service != null : "context lookup failed";
            return service.getById(Integer.parseInt(value));
        } catch (Exception e){
            return null;
        }
    }

    /**
     * Responsible for conversion between backing bean and the page.
     */
    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return ((ProductCategory) o).getId().toString();
    }
}