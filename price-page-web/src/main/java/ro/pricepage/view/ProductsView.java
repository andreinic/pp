package ro.pricepage.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;

import org.apache.jackrabbit.core.fs.FileSystemException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

import ro.pricepage.model.PriceDataModel;
import ro.pricepage.model.ProductsDataModel;
import ro.pricepage.persistence.entities.Producer;
import ro.pricepage.persistence.entities.Product;
import ro.pricepage.persistence.entities.ProductCategory;
import ro.pricepage.persistence.entities.ProductStore;
import ro.pricepage.persistence.entities.Store;
import ro.pricepage.service.FileService;
import ro.pricepage.service.ProductCategoriesService;
import ro.pricepage.service.ProductsService;
import ro.pricepage.service.StoresService;
import ro.pricepage.view.dto.ImageDTO;

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

	@Inject
	private FileService fileService;
	
	@Inject
	private StoresService storesService;

	@Inject
	private PriceDataModel pricesModel;

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
	private List<ImageDTO> editedProductImages;

	private Store newPriceStore;
	private String newPriceValue;

	private String selectedImagePath;
	private ProductStore selectedPrice;
	
	private List<Store> allStores;

	@PostConstruct
	public void init() {
		categories = categoriesService.list();
		allStores = storesService.findAll();
	}

	// Actions
	// ---------------------------------------------------------------------------------------------
	public void onSave(ActionEvent e) {
		Product product = new Product(newProductName, newProductCategory,
				newProductProducer, newProductQuantity, newProductMeasureUnit,
				newProductDescription);
		productsService.add(product);
	}

	public void onUpdate(ActionEvent e) {
		selectedProduct.setName(editedProductName);
		selectedProduct.setCategory(editedProductCategory);
		selectedProduct.setProducer(editedProductProducer);
		selectedProduct.setQuantity(Double.parseDouble(editedProductQuantity));
		selectedProduct.setMeasureUnit(editedProductMeasureUnit);
		selectedProduct.setDescription(editedProductDescription);
		selectedProduct = productsService.update(selectedProduct);
	}

	public void onSelect(SelectEvent e) {
		editedProductName = selectedProduct.getName();
		editedProductCategory = selectedProduct.getCategory();
		editedProductProducer = selectedProduct.getProducer();
		editedProductQuantity = selectedProduct.getQuantity().toString();
		editedProductMeasureUnit = selectedProduct.getMeasureUnit();
		editedProductDescription = selectedProduct.getDescription();

		refreshEditedProductImages();
	}

	public String onDelete(){
		int id = selectedProduct.getId().intValue();
		try {
			fileService.deleteProductNode(id);
		} catch (RepositoryException | FileSystemException e) {
			e.printStackTrace();
			if(!(e.getCause() instanceof PathNotFoundException)){
				return "";
			}
		}

		productsService.deleteProduct(selectedProduct);
		selectedProduct = null;
		clearEditedData();
		refreshEditedProductImages();
		return "";
	}

	public void savePrice(ActionEvent e) {
		ProductStore ps = productsService.getPriceForStoreAndProduct(newPriceStore.getId(), selectedProduct.getId());
		if(ps == null){
			ps = new ProductStore();
			ps.setProduct(selectedProduct);
			ps.setStore(newPriceStore);
			ps.setPrice(Double.valueOf(newPriceValue));
			productsService.add(ps);
			return;
		}
		ps.setPrice(Double.valueOf(newPriceValue));
		productsService.update(ps);
		return;
	}

	public String deletePrice(){
		productsService.deleteProductStore(selectedPrice);
		return "";
	}

	public String setDefaultImage() {
		try {
			fileService.setHeadForProduct(selectedProduct.getId().intValue(),
					selectedImagePath.substring(selectedImagePath
							.lastIndexOf('/') + 1));
			refreshEditedProductImages();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: put some message
			// FacesContext.getCurrentInstance().addMessage(arg0, arg1);
		}
		return "";
	}

	public String deleteImage(){
		try{
			fileService.deleteNode(selectedImagePath);
			refreshEditedProductImages();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: put some message
			// FacesContext.getCurrentInstance().addMessage(arg0, arg1);
		}
		return "";
	}
	
	public void handleFileUpload(FileUploadEvent evt){
		UploadedFile file = evt.getFile();
		try {
			fileService.uploadImageForProduct(selectedProduct.getId().intValue(), file.getInputstream(), false, true);
		} catch (RepositoryException | FileSystemException
				| IOException e) {
			// TODO: faces message
			e.printStackTrace();
		}
		refreshEditedProductImages();
	}

	private void refreshEditedProductImages() {
		editedProductImages = new ArrayList<>();
		if (selectedProduct != null) {
			Integer productId = selectedProduct.getId();
			String headPath = null;
			try {
				headPath = fileService.getImagePathForProductHead(productId
						.intValue());
			} catch (Exception e) {
				// TODO: put some message
				// FacesContext.getCurrentInstance().addMessage(arg0, arg1);
			}

			List<String> paths = null;
			try {
				paths = fileService.getImagePathsForProduct(productId
						.intValue());
			} catch (Exception e) {
				// TODO: put some message
			}

			for (String path : paths) {
				boolean isHead = path.equals(headPath);
				if (isHead) {
					editedProductImages
					.add(0, new ImageDTO(path, Boolean.TRUE));
					continue;
				}
				editedProductImages.add(new ImageDTO(path, Boolean.FALSE));
			}
		}
	}
	
	private void clearEditedData(){
		editedProductCategory = null;
		editedProductDescription = null;
		editedProductImages = null;
		editedProductMeasureUnit = null;
		editedProductName = null;
		editedProductProducer = null;
		editedProductQuantity = null;
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

	public PriceDataModel getPricesModel() {
		return pricesModel;
	}

	public Store getNewPriceStore() {
		return newPriceStore;
	}

	public void setNewPriceStore(Store newPriceStore) {
		this.newPriceStore = newPriceStore;
	}

	public String getNewPriceValue() {
		return newPriceValue;
	}

	public void setNewPriceValue(String newPriceValue) {
		this.newPriceValue = newPriceValue;
	}

	public List<ImageDTO> getEditedProductImages() {
		return editedProductImages;
	}

	public void setEditedProductImages(List<ImageDTO> editedProductImages) {
		this.editedProductImages = editedProductImages;
	}

	public String getSelectedImagePath() {
		return selectedImagePath;
	}

	public void setSelectedImagePath(String selectedImagePath) {
		this.selectedImagePath = selectedImagePath;
	}

	public ProductStore getSelectedPrice() {
		return selectedPrice;
	}

	public void setSelectedPrice(ProductStore selectedPrice) {
		this.selectedPrice = selectedPrice;
	}

	public List<Store> getAllStores() {
		return allStores;
	}

	public void setAllStores(List<Store> allStores) {
		this.allStores = allStores;
	}
}
