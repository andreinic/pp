package ro.pricepage.view;

import com.ocpsoft.pretty.faces.annotation.URLMapping;

import javax.inject.Named;

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

}
