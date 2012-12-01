package ro.pricepage.view;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import org.primefaces.event.RowEditEvent;
import ro.pricepage.model.ProducerDataModel;
import ro.pricepage.persistence.entities.Producer;
import ro.pricepage.service.ProducersService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

/**
 * User: toev
 * Date: 11/1/12
 * Time: 10:48 PM
 */
@ManagedBean(name = "producersView")
@URLMapping(id = "producersView", pattern = "/admin/producatori", viewId = "/WEB-INF/view/admin/producers.jsf")
public class ProducersView implements Serializable
{
    private static final long serialVersionUID = 1L;

    @EJB
    private ProducersService producersService;

    private ProducerDataModel dataModel;

    private List<Producer> producers;
    private Producer selectedItem;

    private String name;

    @PostConstruct
    public void init(){
        producers = producersService.listProducers();
        dataModel = new ProducerDataModel(producers);
    }

    public void doSave(){
        if(isUnique(name)){
            Producer prod = producersService.addProducer(name);
            name = "";
            producers.add(prod);
        } else {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, name + " already exists", ""));
            fc.responseComplete();
        }
    }

    //TODO Need unqiue validation here
    public void onEdit(RowEditEvent event) throws Exception{
        Producer prod = ((Producer) event.getObject());
        Integer id = prod.getId();
        String name = prod.getName();
        producersService.updateProducer(id, name);
    }

    public void onDelete() throws Exception{
        producersService.deleteProducer(selectedItem.getId());
        selectedItem = null;
    }

    private boolean isUnique(String name){
        return producersService.getProducer(name) == null;
    }

    public List<Producer> getProducers(){ return producers; }

    public String getName(){ return name; }
    public void setName(String value) { name = value; }

    public Producer getSelectedItem(){ return selectedItem; }
    public void setSelectedItem(Producer value){ selectedItem = value; }

    public ProducerDataModel getDataModel(){ return dataModel; }
}
