package ro.pricepage.view;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import ro.pricepage.persistence.entities.StoreChain;
import ro.pricepage.service.StoresService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.List;

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
    
    @EJB
    private StoresService storesService;

    private TreeNode root;
    
    private String selectedChain;
    
    private List<String> allChainNames;

    @PostConstruct
    public void init()
    {
    	findAllChainNames();
    	
        root = new DefaultTreeNode("root", null);

        TreeNode cat1 = new DefaultTreeNode(new ShopDoc("Brasov"), root);

        TreeNode cat11 = new DefaultTreeNode(new ShopDoc("Hipermaket"), cat1);
        TreeNode cat12 = new DefaultTreeNode(new ShopDoc("Minimarket"), cat1);

        TreeNode cat111 = new DefaultTreeNode(new ShopDoc("Carefour"), cat11);
        TreeNode cat112 = new DefaultTreeNode(new ShopDoc("Real"), cat11);

        TreeNode cat1111 = new DefaultTreeNode("document", new ShopDoc("Calea Bucuresti nr.107"), cat111);
    }
    
    public void onSave(){
    	StoreChain chain = storesService.findSingleStoreChainByName(selectedChain);
    	if(chain == null){
    		chain = storesService.saveChainWithName(selectedChain);
    	}
    	
    }

    public void dummy(){
    }
    
    private void findAllChainNames(){
    	allChainNames = storesService.findAllStoreChainNames();
    }

    public TreeNode getRoot(){return root;}

    public List<String> getAllChainNames() {
		return allChainNames;
	}

	public String getSelectedChain() {
		return selectedChain;
	}

	public void setSelectedChain(String selectedChain) {
		this.selectedChain = selectedChain;
	}

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
