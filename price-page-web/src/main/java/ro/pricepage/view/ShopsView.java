package ro.pricepage.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import ro.pricepage.persistence.entities.StoreChain;
import ro.pricepage.persistence.entities.StoreType;
import ro.pricepage.service.LocalitiesService;
import ro.pricepage.service.StoresService;

import com.ocpsoft.pretty.faces.annotation.URLMapping;

/**
 * Created with IntelliJ IDEA.
 * User: toev
 * Date: 11/1/12
 * Time: 10:49 PM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean(name = "shopsView")
@URLMapping(id = "shopsView", pattern = "/admin/magazine", viewId = "/WEB-INF/view/admin/shops.jsf")
@SessionScoped
public class ShopsView implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @EJB
    private StoresService storesService;
    
    @EJB
    private LocalitiesService localitiesService;

    private TreeNode root;
    
    private String selectedChain;
    private String selectedType;
    private String storeName;
    private String selectedCounty;
    private String selectedLocality;
    
    private List<String> allChainNames;
    private List<String> allTypeNames;
    private List<String> allCounties;
    private List<String> localities;

    @PostConstruct
    public void init()
    {
    	findAllChainNames();
    	findAllTypeNames();
    	findAllCounties();
    	
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
    	
    	StoreType type = storesService.findSingleStoreTypeByName(selectedType);
    	if(type == null){
    		type = storesService.saveTypeWithName(selectedType);
    	}
    	
//    	Store store = new Store(storeName, chain, type); 
    }

    public void dummy(){
    }
    
    private void findAllChainNames(){
    	allChainNames = storesService.findAllStoreChainNames();
    }
    
    private void findAllTypeNames(){
    	allTypeNames = storesService.findAllStoreTypeNames();
    }
    
    private void findAllCounties(){
    	allCounties = localitiesService.findAllCounties();
    }
    
    public void populateLocalities(AjaxBehaviorEvent event){
    	localities = localitiesService.findAllCitiesForCounty(selectedCounty);
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

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public List<String> getAllTypeNames() {
		return allTypeNames;
	}

	public void setAllTypeNames(List<String> allTypeNames) {
		this.allTypeNames = allTypeNames;
	}

	public String getSelectedType() {
		return selectedType;
	}

	public void setSelectedType(String selectedType) {
		this.selectedType = selectedType;
	}

	public List<String> getAllCounties() {
		return allCounties;
	}

	public void setAllCounties(List<String> allCounties) {
		this.allCounties = allCounties;
	}

	public List<String> getLocalities() {
		return localities;
	}

	public void setLocalities(List<String> localities) {
		this.localities = localities;
	}

	public String getSelectedCounty() {
		return selectedCounty;
	}

	public void setSelectedCounty(String selectedCounty) {
		this.selectedCounty = selectedCounty;
	}

	public String getSelectedLocality() {
		return selectedLocality;
	}

	public void setSelectedLocality(String selectedLocality) {
		this.selectedLocality = selectedLocality;
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
