package ro.pricepage.model;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SelectableDataModel;
import org.primefaces.model.SortOrder;
import ro.pricepage.persistence.entities.Product;
import ro.pricepage.service.ProductsService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named(value = "productsDataModel")
public class ProductsDataModel extends LazyDataModel<Product> implements SelectableDataModel<Product>
{
	private static final long serialVersionUID = -8530048834277234726L;

	@Inject
    private ProductsService productsService;

    private List<Product> products;

    @Override
    public Object getRowKey(Product product) {
        return product.getName();
    }

    @Override
    public Product getRowData(String rowKey) {
        for(Product product : products){
            if(product.getName().equals(rowKey)){
                return product;
            }
        }
        return null;
    }

    @Override
    public List<Product> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        products = productsService.list(first, first + pageSize);
        setRowCount(productsService.count().intValue());
        setPageSize(pageSize);
        return products;
    }
}
