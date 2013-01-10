package ro.pricepage.converters;

import ro.pricepage.persistence.entities.Producer;
import ro.pricepage.service.ProducersService;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.Serializable;

@FacesConverter(forClass = Producer.class, value = "producerConverter")
@RequestScoped
public class ProducerConverter implements Converter, Serializable
{
    private static final long serialVersionUID = 1L;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        try{
            Context ctx = new InitialContext();
            ProducersService service = (ProducersService) ctx.lookup("java:global/price-page/ProducersService");
            assert service != null : "context lookup failed";
            return service.get(Integer.parseInt(value));
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        String id = "";
        try{
            id = ((Producer) o).getId().toString();
        } catch (Exception e){}
        return id;
    }
}
