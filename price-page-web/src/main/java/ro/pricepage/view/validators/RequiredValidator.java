package ro.pricepage.view.validators;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * User: radutoev
 * Date: 01.12.2012
 * Time: 19:22
 */
@FacesValidator(value = "requiredValidator")
@RequestScoped
public class RequiredValidator implements Validator
{

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        if(o == null || "".equals(o.toString().trim())){
            FacesMessage fm = new FacesMessage();
            String messageStr = (String) uiComponent.getAttributes().get("requiredMessage");
            if(messageStr == null){
                messageStr = "Please enter data";
            }
            fm.setDetail(messageStr);
            fm.setSummary(messageStr);
            fm.setSeverity(FacesMessage.SEVERITY_WARN);
            throw new ValidatorException(fm);
        }
    }
}
