package ro.pricepage.view;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import ro.pricepage.persistence.entities.Producer;
import ro.pricepage.service.ProducersService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: toev
 * Date: 11/1/12
 * Time: 10:48 PM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean(name = "producersView")
@URLMapping(id = "producersView", pattern = "/admin/producatori", viewId = "/WEB-INF/view/admin/producers.jsf")
public class ProducersView implements Serializable
{
    private static final long serialVersionUID = 1L;

    @EJB
    private ProducersService producersService;

    private List<Producer> producers;

    @PostConstruct
    public void init(){
        producers = producersService.listProducers();
    }

    public void addProducer(){
        System.out.println("add producer");
    }

    public List<Producer> getProducers(){ return producers; }

//    public class Producer{
//        private Integer id;
//        private String name;
//
//        public Producer(Integer id, String name){
//            this.id = id;
//            this.name = name;
//        }
//
//        public Integer getId(){ return id; }
//        public void setId(Integer id){ this.id = id; }
//
//        public String getName(){ return name; }
//        public void setName(String name){ this.name = name; }
//    }
}
