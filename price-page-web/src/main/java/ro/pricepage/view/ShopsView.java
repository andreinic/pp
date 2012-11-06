package ro.pricepage.view;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: toev
 * Date: 11/1/12
 * Time: 10:49 PM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean(name = "shopsView")
@URLMapping(id = "shopsView", pattern = "/admin/magazine", viewId = "/WEB-INF/view/admin/shops.jsf")
public class ShopsView implements Serializable
{
    private static final long serialVersionUID = 1L;

    private TreeNode root;

    @PostConstruct
    public void init()
    {
        root = new DefaultTreeNode("root", null);

        TreeNode cat1 = new DefaultTreeNode(new ShopDoc("Brasov"), root);

        TreeNode cat11 = new DefaultTreeNode(new ShopDoc("Hipermaket"), cat1);
        TreeNode cat12 = new DefaultTreeNode(new ShopDoc("Minimarket"), cat1);

        TreeNode cat111 = new DefaultTreeNode(new ShopDoc("Carefour"), cat11);
        TreeNode cat112 = new DefaultTreeNode(new ShopDoc("Real"), cat11);

        TreeNode cat1111 = new DefaultTreeNode("document", new ShopDoc("Calea Bucuresti nr.107"), cat111);
    }

    public void dummy(){
    }

    public TreeNode getRoot(){return root;}

    public class ShopDoc
    {
        private String name;

        public ShopDoc(String name)
        {
            this.name = name;
        }

        public String getName(){return name;}
        public void setName(String name){this.name = name;}
    }
}
