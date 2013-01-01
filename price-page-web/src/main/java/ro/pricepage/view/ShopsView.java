package ro.pricepage.view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.faces.context.RenderScoped;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import ro.pricepage.persistence.entities.Location;
import ro.pricepage.persistence.entities.Store;
import ro.pricepage.persistence.entities.StoreChain;
import ro.pricepage.persistence.entities.StoreType;
import ro.pricepage.service.LocalitiesService;
import ro.pricepage.service.StoresService;

import com.ocpsoft.pretty.faces.annotation.URLMapping;

@Named(value = "shopsView")
@RenderScoped
@URLMapping(id = "shopsView", pattern = "/admin/magazine", viewId = "/WEB-INF/view/admin/shops.jsf")
public class ShopsView implements Serializable
{
	private static final String DEFAULT_COUNTY = "Brasov";

	private static final long serialVersionUID = 1L;

    @Inject
	private StoresService storesService;

    @Inject
	private LocalitiesService localitiesService;

	private TreeNode treeRoot;

	private String selectedChain;
	private String selectedType;
	private String storeName;
	private String selectedCounty;
	private Integer selectedLocalityId;
	private Double latitude;
	private Double longitude;
	private String address;
	private String zip;
	private MapModel map;
	private String url;

	private List<String> allChainNames;
	private List<String> allTypeNames;
	private List<String> allCounties;
	private List<Location> localities;
	private Map<Integer, Location> localitiesMap; 

	@PostConstruct
	public void init()
	{
		findAllChainNames();
		findAllTypeNames();
		findAllCounties();
		selectedCounty = DEFAULT_COUNTY;
		populateLocalities(DEFAULT_COUNTY);

		map = new DefaultMapModel();
		
		initTree();
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

		Location locality = findLocality(selectedLocalityId);

		Store store = new Store(storeName, chain, type, locality, address, zip, longitude, latitude, url);
		storesService.saveStore(store);
	}

	public void showOnMap(ActionEvent event){
		map.getMarkers().clear();
		map.addOverlay(new Marker(new LatLng(latitude.doubleValue(), longitude.doubleValue())));
	}

	public void onPointSelect(PointSelectEvent event){
		map.getMarkers().clear();
		LatLng latlng = event.getLatLng();
		map.addOverlay(new Marker(latlng));
		latitude = Double.valueOf(latlng.getLat());
		longitude = Double.valueOf(latlng.getLng());
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

	private void populateLocalities(String countyName){
		localities = localitiesService.findAllLocationsForCounty(countyName);
		localitiesMap = new HashMap<Integer, Location>();
		for(Location locality : localities){
			localitiesMap.put(locality.getId(), locality);
		}
	}

	private Location findLocality(Integer id){
		return localitiesMap.get(id);
	}

	public void populateLocalities(AjaxBehaviorEvent event){
		populateLocalities(selectedCounty);
	}
	
	private void initTree(){
		treeRoot = new DefaultTreeNode("root", null);
		List<String> allCities = localitiesService.findAllCitiesWithStores();
		for(String city : allCities){
			new DefaultTreeNode(ShopDoc.Type.city.name(), new ShopDoc(city), treeRoot);
		}
	}
	
	public void onTreeNodeExpand(NodeExpandEvent e){
		TreeNode treeNode = e.getTreeNode();
		switch(ShopDoc.Type.valueOf(treeNode.getType())){
		case city:
			
			break;
		}
	}

	public TreeNode getTreeRoot(){
		return treeRoot;
	}

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

	public String getSelectedCounty() {
		return selectedCounty;
	}

	public void setSelectedCounty(String selectedCounty) {
		this.selectedCounty = selectedCounty;
	}

	public Integer getSelectedLocalityId() {
		return selectedLocalityId;
	}

	public void setSelectedLocalityId(Integer selectedLocalityId) {
		this.selectedLocalityId = selectedLocalityId;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public MapModel getMap() {
		return map;
	}

	public void setMap(MapModel map) {
		this.map = map;
	}

	public List<Location> getLocalities() {
		return localities;
	}

	public void setLocalities(List<Location> localities) {
		this.localities = localities;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getUrl() {
		return url;
	}

	public void setURL(String url) {
		this.url = url;
	}

	public static class ShopDoc
	{
		public enum Type{
			city,chain,store;
		}
		private String name;
		private String address;
		private String type;

		public ShopDoc(String name)
		{
			this.name = name;
		}

		public ShopDoc(String name, String address, String type)
		{
			this.name = name;
			this.address = address;
			this.type = type;
		}

		public String getName(){
			return name;
		}
		public void setName(String name){
			this.name = name;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}
}
