package ro.pricepage.converters;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;

import ro.pricepage.persistence.entities.ProductStore;
import ro.pricepage.service.ProductsService;

@FacesConverter(forClass = ProductStore.class, value = "productStoreConverter")
@RequestScoped
public class ProductStoreConverter implements Serializable, Converter {

	private static final long serialVersionUID = 3723641539976007307L;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		try{
            Context ctx = new InitialContext();
            ProductsService service = (ProductsService) ctx.lookup("java:global/price-page/ProductsService");
            assert service != null : "context lookup failed";
            return service.getInstance(Integer.parseInt(value));
        } catch (Exception e){
            return null;
        }
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object o) {
		String id = "";
        try{
            id = ((ProductStore) o).getId().toString();
        } catch (Exception e){}
        return id;
	}

}
