package ro.pricepage.view;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import ro.pricepage.persistence.entities.ProductCategory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: toev
 * Date: 11/1/12
 * Time: 10:24 PM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean(name = "categoriesView")
@URLMapping(id = "categoriesView", pattern = "/admin/categorii", viewId = "/WEB-INF/view/admin/categories.jsf")
public class CategoriesView implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String dummyTxt;
    private TreeNode root;

    @PostConstruct
    public void init(){
        root = new DefaultTreeNode("root", null);

        TreeNode cat1 = new DefaultTreeNode(new CategoryDoc("Cat1"), root);
        TreeNode cat2 = new DefaultTreeNode(new CategoryDoc("Cat2"), root);

        TreeNode cat11 = new DefaultTreeNode("document", new CategoryDoc("Cat11"), cat1);
        TreeNode cat12 = new DefaultTreeNode("document", new CategoryDoc("Cat12"), cat1);
        TreeNode cat13 = new DefaultTreeNode("document", new CategoryDoc("Cat12"), cat1);
    }

    public List<String> dummyComplete(String query){
        List<String> res = new ArrayList<>();
        for(int i = 0 ; i < 10 ; ++i){
            res.add(query+i);
        }
        return res;
    }

    public String getDummyTxt(){ return dummyTxt; }
    public void setDummyTxt(String val){ dummyTxt = val; }

    public TreeNode getRoot(){return root;}

    public class CategoryDoc
    {
        private String name;

        public CategoryDoc(String name)
        {
            this.name = name;
        }

        public String getName(){return name;}
        public void setName(String name){this.name = name;}
    }
}
