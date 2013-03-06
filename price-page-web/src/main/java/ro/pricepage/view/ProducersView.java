package ro.pricepage.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.faces.context.RenderScoped;

import ro.pricepage.model.ProducerDataModel;
import ro.pricepage.persistence.entities.Producer;
import ro.pricepage.service.ProducersService;

import com.ocpsoft.pretty.faces.annotation.URLMapping;

/**
 * User: toev Date: 11/1/12 Time: 10:48 PM
 */
@Named(value = "producersView")
@RenderScoped
@URLMapping(id = "producersView", pattern = "/admin/producatori", viewId = "/WEB-INF/view/admin/producers.jsf")
public class ProducersView implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private ProducersService producersService;

	private transient ProducerDataModel dataModel;

	private List<Producer> producers;

	private String newName;

	@PostConstruct
	public void init() {
		System.out.println("init");
		producers = producersService.list();
	}

	// Actions
	// ---------------------------------------------------------------------------------------------
	public void onSave() {
		Producer producer = dataModel.getRowData();
		try {
			producersService.update(producer);
		} catch (Exception e) {
			System.out.println("error updating producer");
		}
	}

	public void onDelete() {
		Producer producer = dataModel.getRowData();
		assert producer.getId() != null;
		try {
			producersService.delete(producer.getId());
			producers.remove(producers.indexOf(producer));
		} catch (Exception e) {
			System.out.println("error deleting producer");
		}
	}

	// Methods
	// ----------------------------------------------------------------------------------------------
	public void addProducer(ActionEvent e) {
		if (newName != null && isUnique(newName)) {
			producers.add(producersService.add(newName));
		}
	}

	private boolean isUnique(String name) {
		return producersService.get(name) == null;
	}

	// Getters and Setters
	// -----------------------------------------------------------------------------------
	public ProducerDataModel getDataModel() {
		if (dataModel == null)
			dataModel = new ProducerDataModel(producers);
		return dataModel;
	}

	public List<Producer> getProducers() {
		return producers;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}
}
