package ro.pricepage.view;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import org.jboss.seam.faces.context.RenderScoped;

import javax.inject.Named;
import java.io.Serializable;

@Named(value = "overviewView")
@RenderScoped
@URLMapping(id = "overviewView", pattern = "/admin", viewId = "/WEB-INF/view/admin/overview.jsf")
public class OverviewView implements Serializable
{
    private static final long serialVersionUID = 1L;
}
