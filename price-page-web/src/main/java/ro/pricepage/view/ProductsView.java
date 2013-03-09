package ro.pricepage.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import ro.pricepage.model.ProductsDataModel;
import ro.pricepage.persistence.entities.Producer;
import ro.pricepage.persistence.entities.Product;
import ro.pricepage.persistence.entities.ProductCategory;
import ro.pricepage.service.ProductCategoriesService;
import ro.pricepage.service.ProductsService;

import com.ocpsoft.pretty.faces.annotation.URLMapping;

/**
 * User: radutoev Date: 27.10.2012 Time: 16:22
 */
@Named(value = "productsView")
@ViewScoped
@URLMapping(id = "productsView", pattern = "/admin/produse", viewId = "/WEB-INF/view/admin/products.jsf")
public class ProductsView implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<ProductCategory> categories;
	private Product selectedProduct;

	@Inject
	private ProductsDataModel productsModel;

	@Inject
	private ProductCategoriesService categoriesService;
	
	@Inject
	private ProductsService productsService;
	
	private String newProductName;
	private ProductCategory newProductCategory;
	private Producer newProductProducer;
	private String newProductQuantity;
	private String newProductMeasureUnit; 
	private String newProductDescription;	

	private String editedProductName;
	private ProductCategory editedProductCategory;
	private Producer editedProductProducer;
	private String editedProductQuantity;
	private String editedProductMeasureUnit; 
	private String editedProductDescription;

	@PostConstruct
	public void init() {
		categories = categoriesService.list();
	}

	// Actions
	// ---------------------------------------------------------------------------------------------
	public void onSave(ActionEvent e) {
		Product product = new Product(newProductName, newProductCategory, newProductProducer, newProductQuantity, newProductMeasureUnit, newProductDescription);
		productsService.add(product);
	}
	
	public void onUpdate(ActionEvent e){
		
	}
	
	public void onSelect(SelectEvent e){
		editedProductName = selectedProduct.getName();
		editedProductCategory = selectedProduct.getCategory();
		editedProductProducer = selectedProduct.getProducer();
		editedProductQuantity = selectedProduct.getQuantity().toString();
		editedProductMeasureUnit = selectedProduct.getMeasureUnit();
		editedProductDescription = selectedProduct.getDescription();
	}

	// Getters and setters
	// ---------------------------------------------------------------------------------

	public List<ProductCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<ProductCategory> categories) {
		this.categories = categories;
	}

	public Product getSelectedProduct() {
		return selectedProduct;
	}

	public void setSelectedProduct(Product product) {
		this.selectedProduct = product;
	}

	public ProductsDataModel getProductsModel() {
		return productsModel;
	}

	public String getNewProductName() {
		return newProductName;
	}

	public void setNewProductName(String newProductName) {
		this.newProductName = newProductName;
	}

	public ProductCategory getNewProductCategory() {
		return newProductCategory;
	}

	public void setNewProductCategory(ProductCategory newProductCategory) {
		this.newProductCategory = newProductCategory;
	}

	public Producer getNewProductProducer() {
		return newProductProducer;
	}

	public void setNewProductProducer(Producer newProductProducer) {
		this.newProductProducer = newProductProducer;
	}

	public String getNewProductQuantity() {
		return newProductQuantity;
	}

	public void setNewProductQuantity(String newProductQuantity) {
		this.newProductQuantity = newProductQuantity;
	}

	public String getNewProductMeasureUnit() {
		return newProductMeasureUnit;
	}

	public void setNewProductMeasureUnit(String newProductMeasureUnit) {
		this.newProductMeasureUnit = newProductMeasureUnit;
	}

	public String getNewProductDescription() {
		return newProductDescription;
	}

	public void setNewProductDescription(String newProductDescription) {
		this.newProductDescription = newProductDescription;
	}

	public String getEditedProductName() {
		return editedProductName;
	}

	public void setEditedProductName(String editedProductName) {
		this.editedProductName = editedProductName;
	}

	public ProductCategory getEditedProductCategory() {
		return editedProductCategory;
	}

	public void setEditedProductCategory(ProductCategory editedProductCategory) {
		this.editedProductCategory = editedProductCategory;
	}

	public Producer getEditedProductProducer() {
		return editedProductProducer;
	}

	public void setEditedProductProducer(Producer editedProductProducer) {
		this.editedProductProducer = editedProductProducer;
	}

	public String getEditedProductQuantity() {
		return editedProductQuantity;
	}

	public void setEditedProductQuantity(String editedProductQuantity) {
		this.editedProductQuantity = editedProductQuantity;
	}

	public String getEditedProductMeasureUnit() {
		return editedProductMeasureUnit;
	}

	public void setEditedProductMeasureUnit(String editedProductMeasureUnit) {
		this.editedProductMeasureUnit = editedProductMeasureUnit;
	}

	public String getEditedProductDescription() {
		return editedProductDescription;
	}

	public void setEditedProductDescription(String editedProductDescription) {
		this.editedProductDescription = editedProductDescription;
	}
}
