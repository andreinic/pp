package ro.pricepage.view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;

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

/**
 * Created with IntelliJ IDEA.
 * User: toev
 * Date: 11/1/12
 * Time: 10:49 PM
 * To change this template use File | Settings | File Templates.
 */
@Named(value = "shopsView")
@URLMapping(id = "shopsView", pattern = "/admin/magazine", viewId = "/WEB-INF/view/admin/shops.jsf")
@ViewScoped
public class ShopsView implements Serializable
{
	private static final String DEFAULT_COUNTY = "Brasov";

	private static final long serialVersionUID = 1L;

	@EJB
	private StoresService storesService;

	@EJB
	private LocalitiesService localitiesService;

	private TreeNode treeRoot;
	private Store selectedStore;

	private State inWorkState;

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

	public void onTreePick(){
		saveInWorkDetails();
		populateFromSelectedStore();
	}

	public void onUpdate(){
		StoreChain chain = storesService.findSingleStoreChainByName(selectedChain);
		if(chain == null){
			chain = storesService.saveChainWithName(selectedChain);
		}

		StoreType type = storesService.findSingleStoreTypeByName(selectedType);
		if(type == null){
			type = storesService.saveTypeWithName(selectedType);
		}

		Location locality = findLocality(selectedLocalityId);

		selectedStore.setName(storeName);
		selectedStore.setChain(chain);
		selectedStore.setStoreType(type);
		selectedStore.setLocality(locality);
		selectedStore.setAddress(address);
		selectedStore.setZip(zip);
		selectedStore.setLongitude(longitude);
		selectedStore.setLatitude(latitude);
		selectedStore.setUrl(url);

		storesService.saveStore(selectedStore);
		restoreInWorkDetails();
	}

	public void onCancelEdit(){
		restoreInWorkDetails();
	}

	public void showOnMap(ActionEvent event){
		map.getMarkers().clear();
		if(latitude != null && longitude != null){
			map.addOverlay(new Marker(new LatLng(latitude.doubleValue(), longitude.doubleValue())));
		}
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

	private void saveInWorkDetails(){
		inWorkState = new State();
		inWorkState.address = address;
		inWorkState.latitude = latitude;
		inWorkState.longitude = longitude;
		inWorkState.selectedChain = selectedChain;
		inWorkState.selectedCounty = selectedCounty;
		inWorkState.selectedLocalityId = selectedLocalityId;
		inWorkState.selectedType = selectedType;
		inWorkState.storeName = storeName;
		inWorkState.url = url;
		inWorkState.zip = zip;
	}

	private void restoreInWorkDetails(){
		address = inWorkState.address;
		latitude = inWorkState.latitude;
		longitude = inWorkState.longitude;
		selectedChain = inWorkState.selectedChain;
		selectedCounty = inWorkState.selectedCounty;
		selectedLocalityId = inWorkState.selectedLocalityId;
		selectedType = inWorkState.selectedType;
		storeName = inWorkState.storeName;
		url = inWorkState.url;
		zip = inWorkState.zip;
		showOnMap(null);
		selectedStore = null;
	}

	private void populateFromSelectedStore(){
		address = selectedStore.getAddress();
		latitude = selectedStore.getLatitude();
		longitude = selectedStore.getLongitude();
		selectedChain = selectedStore.getChain().getName();
		selectedCounty = selectedStore.getLocality().getCounty();
		selectedLocalityId = selectedStore.getLocality().getId();
		selectedType = selectedStore.getStoreType().getName();
		storeName = selectedStore.getName();
		url = selectedStore.getUrl();
		zip = selectedStore.getZip();
		showOnMap(null);
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
			new DefaultTreeNode("dummy", new Object(), new DefaultTreeNode(ShopDoc.Type.city.name(), new ShopDoc(city), treeRoot));
		}
	}

	public void onTreeNodeExpand(NodeExpandEvent e){
		TreeNode treeNode = e.getTreeNode();
		treeNode.getChildren().clear();
		switch(ShopDoc.Type.valueOf(treeNode.getType())){
		case city:
			List<String> allChains = storesService.findAllChainNamesInCity(((ShopDoc) treeNode.getData()).getName());
			for(String chain : allChains){
				new DefaultTreeNode("dummy", new Object(), new DefaultTreeNode(ShopDoc.Type.chain.name(), new ShopDoc(chain), treeNode));
			}
			break;
		case chain:
			List<Store> allStores = storesService.findAllStoresByChainAndCity(((ShopDoc) treeNode.getData()).getName(), ((ShopDoc) treeNode.getParent().getData()).getName());
			for(Store store : allStores){
				new DefaultTreeNode(ShopDoc.Type.store.name(), new ShopDoc(store.getName(), store), treeNode);
			}
			break;
		default:
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

	public Store getSelectedStore() {
		return selectedStore;
	}

	public void setSelectedStore(Store selectedStore) {
		this.selectedStore = selectedStore;
	}

	public static class ShopDoc
	{
		public enum Type{
			city,chain,store;
		}
		private String name;
		private Store store;

		public ShopDoc(String name)
		{
			this.name = name;
		}

		public ShopDoc(String name, Store store)
		{
			this.name = name;
			this.store = store;
		}

		public String getName(){
			return name;
		}
		public void setName(String name){
			this.name = name;
		}

		public Store getStore() {
			return store;
		}

		public void setStore(Store store) {
			this.store = store;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((store == null) ? 0 : store.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ShopDoc other = (ShopDoc) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (store == null) {
				if (other.store != null)
					return false;
			} else if (!store.getId().equals(other.store.getId()))
				return false;
			return true;
		}
	}

	private static class State implements Serializable{
		private static final long serialVersionUID = 1L;

		private String selectedChain;
		private String selectedType;
		private String storeName;
		private String selectedCounty;
		private Integer selectedLocalityId;
		private Double latitude;
		private Double longitude;
		private String address;
		private String zip;
		private String url;
	}
}
