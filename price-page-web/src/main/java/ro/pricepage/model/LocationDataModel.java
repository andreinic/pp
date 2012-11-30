package ro.pricepage.model;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import ro.pricepage.persistence.entities.Location;

public class LocationDataModel extends ListDataModel<Location> implements
		SelectableDataModel<Location> {
	
	public LocationDataModel(){
		super();
	}
	
	public LocationDataModel(List<Location> locations){
		super(locations);
	}

	@Override
	public Location getRowData(String rowKey) {
		List<Location> locations = (List<Location>) getWrappedData();
		if (rowKey != null && !rowKey.equals("null")) {
			for (Location location : locations) {
				if (location.getId().compareTo(Integer.valueOf(rowKey)) == 0)
					return location;
			}
		}

		return null;
	}

	@Override
	public Object getRowKey(Location location) {
		return location.getId();
	}

}
