package ro.pricepage.converters;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import ro.pricepage.persistence.entities.Store;
import ro.pricepage.view.ProductsView;

@FacesConverter(forClass = Store.class, value = "storeConverter")
@RequestScoped
public class StoreConverter implements Converter, Serializable {
	private static final long serialVersionUID = -8046601174691966755L;
	
	@Inject
	private ProductsView productsView;

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		try {
			List<Store> allStoresInView = productsView.getAllStores();
			for(Store store : allStoresInView){
				if(store.getId().equals(Integer.valueOf(value))){
					return store;
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
		String id = "";
        try{
            id = ((Store) o).getId().toString();
        } catch (Exception e){}
        return id;
	}

}
