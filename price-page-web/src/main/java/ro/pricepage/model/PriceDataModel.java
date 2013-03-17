package ro.pricepage.model;

import java.util.List;
import java.util.Map;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import ro.pricepage.persistence.entities.ProductStore;
import ro.pricepage.service.ProductsService;
import ro.pricepage.view.ProductsView;

@Named("priceDataModel")
@ViewScoped
public class PriceDataModel extends LazyDataModel<ProductStore> {

	@Inject
	private ProductsView productsView;
	
	@Inject
	private ProductsService productsService;
	
	private static final long serialVersionUID = -4014046030504461916L;

	@Override
	public List<ProductStore> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		if(productsView.getSelectedProduct() == null){
			return null;
		}
		List<ProductStore> prices = productsService.getInstancesForProduct(productsView.getSelectedProduct().getId().intValue(), first, first + pageSize);
		setPageSize(pageSize);
		setRowCount(productsService.countInstancesForProduct(productsView.getSelectedProduct().getId().intValue()).intValue());
		return prices;
	}
	
}
