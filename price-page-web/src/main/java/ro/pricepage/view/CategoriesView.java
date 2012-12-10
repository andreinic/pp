package ro.pricepage.view;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import ro.pricepage.service.ProductCategoriesService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

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

    private String prodCatName;

    @PostConstruct
    public void init(){
        System.out.println("init categoriesView");
        root = new DefaultTreeNode("root", null);
    }

    // Actions ---------------------------------------------------------------------------------------------
    public void onSave(){
        System.out.println(prodCatName);
//        new DefaultTreeNode(categoriesService.add(prodCatName), root);
    }


    // Getters and setters ---------------------------------------------------------------------------------
    public TreeNode getRoot(){return root;}

    public String getProdCatName() { return prodCatName; }
    public void setProdCatName(String value) { prodCatName = value; }


//    private String dummyTxt;
//    private TreeNode root;
//
//    @PostConstruct
//    public void init(){
//        root = new DefaultTreeNode("root", null);
//
//        TreeNode cat1 = new DefaultTreeNode(new CategoryDoc("Cat1"), root);
//        TreeNode cat2 = new DefaultTreeNode(new CategoryDoc("Cat2"), root);
//
//        TreeNode cat11 = new DefaultTreeNode("document", new CategoryDoc("Cat11"), cat1);
//        TreeNode cat12 = new DefaultTreeNode("document", new CategoryDoc("Cat12"), cat1);
//        TreeNode cat13 = new DefaultTreeNode("document", new CategoryDoc("Cat12"), cat1);
//    }
//
//    public List<String> dummyComplete(String query){
//        List<String> res = new ArrayList<>();
//        for(int i = 0 ; i < 10 ; ++i){
//            res.add(query+i);
//        }
//        return res;
//    }
//
//    public String getDummyTxt(){ return dummyTxt; }
//    public void setDummyTxt(String val){ dummyTxt = val; }
//
//    public TreeNode getRoot(){return root;}
//
//    public class CategoryDoc
//    {
//        private String name;
//
//        public CategoryDoc(String name)
//        {
//            this.name = name;
//        }
//
//        public String getName(){return name;}
//        public void setName(String name){this.name = name;}
//    }
}
