package ro.pricepage.view;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import ro.pricepage.persistence.entities.ProductCategory;
import ro.pricepage.service.ProductCategoriesService;

import com.ocpsoft.pretty.faces.annotation.URLMapping;

/**
 * @author radutoev
 */
@Named(value = "categoriesView")
@ViewScoped
@URLMapping(id = "categoriesView", pattern = "/admin/categorii", viewId = "/WEB-INF/view/admin/categories.jsf")
public class CategoriesView implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String TREE_SELECTION_MODE_SINGLE = "single";
	@Inject
	private ProductCategoriesService categoriesService;

	private TreeNode root;

	private Set<ProductCategory> parents;
	
	private Set<ProductCategory> all;

	private ProductCategory parent;

	private String prodCatName;

	private TreeNode selectedNode;
	
	private ProductCategory selectedCategory;
	
	private boolean editing;
	
	private boolean selectedHasProducts;
	
	private String treeSelectionMode = TREE_SELECTION_MODE_SINGLE;

	@PostConstruct
	public void init() {
		initAll();
		initTree();
	}
	
	private void initAll(){
		all = new HashSet<>(categoriesService.listAll());		
	}
	
	private void initTree(){
		parents = categoriesService.hierarchy();
		buildTree();		
	}

	// Actions
	// ---------------------------------------------------------------------------------------------
	public void onNew(ActionEvent event) {
		categoriesService.add(prodCatName, parent);
		initAll();
		initTree();
	}
	
	public void onSave(ActionEvent event){
		editing = false;
		treeSelectionMode = TREE_SELECTION_MODE_SINGLE;
		if(selectedCategory != null){
			try {
				categoriesService.merge(selectedCategory);
			} catch (Exception e) {
				System.out.println("exception during update process");
			}
		}
	}

	public void onEdit(ActionEvent event) {
		editing = true;
		treeSelectionMode = null;
	}

	public void onDelete() {
		categoriesService.delete(selectedCategory);
		if(selectedNode.getParent() != null){
			selectedNode.getParent().getChildren().remove(selectedNode);
		}
		initAll();
	}

	public void onSelect(NodeSelectEvent event) {
		selectedNode = event.getTreeNode();
		selectedCategory = (ProductCategory) selectedNode.getData();
		selectedHasProducts = categoriesService.hasProducts(selectedCategory.getId());
	}

	// Methods
	// ---------------------------------------------------------------------------------------------
	private void buildTree() {
		root = new DefaultTreeNode("root", null);
		for (ProductCategory parent : parents) {
			buildTree(parent, root);
		}
	}

	private void buildTree(ProductCategory category, TreeNode parent) {
		TreeNode p = new DefaultTreeNode(category, parent);
		for (ProductCategory child : category.getChildren()) {
			buildTree(child, p);
		}
	}

	private ProductCategory getParent(ProductCategory parent) {
		for (ProductCategory p : parents) {
			if (p.equals(parent))
				return p;
		}
		return null;
	}

	// Getters and setters
	// ---------------------------------------------------------------------------------
	public TreeNode getRoot() {
		return root;
	}

	public String getProdCatName() {
		return prodCatName;
	}

	public void setProdCatName(String value) {
		prodCatName = value;
	}

	public Set<ProductCategory> getParents() {
		return parents;
	}

	public void setParents(Set<ProductCategory> parents) {
		this.parents = parents;
	}

	public ProductCategory getParent() {
		return parent;
	}

	public void setParent(ProductCategory parent) {
		this.parent = parent;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public boolean getEditing() {
		return editing;
	}

	public void setEditing(boolean editing) {
		this.editing = editing;
	}

	public ProductCategory getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(ProductCategory selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	public String getTreeSelectionMode() {
		return treeSelectionMode;
	}

	public void setTreeSelectionMode(String treeSelectionMode) {
		this.treeSelectionMode = treeSelectionMode;
	}

	public boolean isSelectedHasProducts() {
		return selectedHasProducts;
	}

	public void setSelectedHasProducts(boolean selectedHasProducts) {
		this.selectedHasProducts = selectedHasProducts;
	}

	public Set<ProductCategory> getAll() {
		return all;
	}

	public void setAll(Set<ProductCategory> all) {
		this.all = all;
	}
}
