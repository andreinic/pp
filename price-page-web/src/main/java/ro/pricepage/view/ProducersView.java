package ro.pricepage.view;

import com.ocpsoft.pretty.faces.annotation.URLMapping;

import javax.inject.Named;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: toev
 * Date: 11/1/12
 * Time: 10:48 PM
 * To change this template use File | Settings | File Templates.
 */
@Named(value = "producersView")
@URLMapping(id = "producersView", pattern = "/admin/producatori", viewId = "/WEB-INF/view/admin/producers.jsf")
public class ProducersView
{
    private List<Producer> producers;

    public ProducersView()
    {

    }

    public List<Producer> getProducers(){ return producers; }

    public class Producer{
        private Integer id;
        private String name;

        public Producer(Integer id, String name){
            this.id = id;
            this.name = name;
        }

        public Integer getId(){ return id; }
        public void setId(Integer id){ this.id = id; }

        public String getName(){ return name; }
        public void setName(String name){ this.name = name; }
    }
}
