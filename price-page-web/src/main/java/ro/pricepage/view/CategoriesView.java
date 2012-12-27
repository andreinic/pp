package ro.pricepage.view;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import ro.pricepage.persistence.entities.ProductCategory;
import ro.pricepage.service.ProductCategoriesService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author radutoev
 */
@ManagedBean(name = "categoriesView")
@ViewScoped
@URLMapping(id = "categoriesView", pattern = "/admin/categorii", viewId = "/WEB-INF/view/admin/categories.jsf")
public class CategoriesView implements Serializable
{
    private static final long serialVersionUID = 1L;

    @EJB
    private ProductCategoriesService categoriesService;

    private TreeNode root;

    private Set<ProductCategory> parents;

    private ProductCategory parent;

    private String prodCatName;

    private TreeNode selectedNode;

    @PostConstruct
    public void init(){
        parents = categoriesService.list();
        buildTree();
    }

    // Actions ---------------------------------------------------------------------------------------------
    public void onSave(){
        TreeNode treeParent = null;
        for(TreeNode node : root.getChildren()){
            if(((ProductCategory) node.getData()).getName().equals(parent.getName())) {
                treeParent = node;
                break;
            }
        }
        parent = getParent(parent);
        assert treeParent != null : "unable to find parent node in tree structure";
        new DefaultTreeNode("document", categoriesService.add(prodCatName, parent), treeParent);
        prodCatName = "";
    }

    public void onEdit(){
        if(selectedNode != null){
            ProductCategory pc = (ProductCategory) selectedNode.getData();
            try{
                pc = categoriesService.update(pc.getId(), pc.getName());
                mainLoop:
                for(TreeNode parent : root.getChildren()){
                    for(TreeNode node : parent.getChildren()){
                        ProductCategory categ = (ProductCategory) node.getData();
                        if(categ.getId().equals(pc.getId())) categ.setName(pc.getName());
                        break mainLoop;
                    }
                }
            } catch (Exception e){
                System.out.println("exception during update process");
            }
        }
    }

    public void onDelete(){
        ProductCategory pc = (ProductCategory) selectedNode.getData();
        categoriesService.delete(pc);
        mainLoop:
        for(TreeNode parent : root.getChildren()){
            for(int i = 0 ; i < parent.getChildren().size() ; ++i){
                if(parent.getChildren().get(i).getData().equals(pc)) {
                    parent.getChildren().remove(i);
                    break mainLoop;
                }
            }
        }
    }

    public void onSelect(NodeSelectEvent event){
        if(event.getTreeNode().getType().equals("document")){
            selectedNode = event.getTreeNode();
        } else {
            selectedNode = null;
        }
    }

    // Methods ---------------------------------------------------------------------------------------------
    private void buildTree(){
        root = new DefaultTreeNode("root", null);
        for(ProductCategory parent : parents){
            TreeNode p = new DefaultTreeNode(parent, root);
            if(!parent.getChildren().isEmpty()){
                for(ProductCategory child : parent.getChildren()){
                    new DefaultTreeNode("document", child, p);
                }
            }
        }
    }

    private ProductCategory getParent(ProductCategory parent){
        for(ProductCategory p : parents){
            if(p.equals(parent)) return p;
        }
        return null;
    }

    // Getters and setters ---------------------------------------------------------------------------------
    public TreeNode getRoot(){return root;}

    public String getProdCatName() { return prodCatName; }
    public void setProdCatName(String value) { prodCatName = value; }

    public Set<ProductCategory> getParents() { return parents; }
    public void setParents(Set<ProductCategory> parents) { this.parents = parents; }

    public ProductCategory getParent() { return parent; }
    public void setParent(ProductCategory parent) { this.parent = parent; }
}
