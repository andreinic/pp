package ro.pricepage.model;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import ro.pricepage.persistence.entities.ProductStore;
import ro.pricepage.persistence.entities.Promo;
import ro.pricepage.service.PromosService;
import ro.pricepage.view.ProductsView;

public class PromoDataModel extends LazyDataModel<Promo> {
	private static final long serialVersionUID = 1824365101992329214L;

	@Inject
	private PromosService promosService;
	
	@Inject
	private ProductsView productsView;

	@Override
	public List<Promo> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map filters) {
		ProductStore ps = productsView.getSelectedPrice();
		if(ps == null){
			return null;
		}
		List<Promo> promos = promosService.getPromosForProductStore(ps.getId(), first, first + pageSize);
		setPageSize(pageSize);
		setRowCount(promosService.countInstancesForProductStore(ps.getId()).intValue());
		return promos;
	}

}
