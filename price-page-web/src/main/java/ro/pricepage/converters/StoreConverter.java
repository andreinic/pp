package ro.pricepage.converters;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import ro.pricepage.persistence.entities.Store;
import ro.pricepage.view.ProductsView;

@FacesConverter(forClass = Store.class, value = "allowedStoreConverter")
@RequestScoped
public class StoreConverter implements Serializable, Converter {
	
	@Inject
	private ProductsView productsView;

	private static final long serialVersionUID = 7224141779661281523L;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		try {
			return productsView.getAllowedStoreById(Integer.parseInt(value));
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object o) {
		String id = "";
		try {
			id = ((Store) o).getId().toString();
		} catch (Exception e) {
		}
		return id;
	}

}
