package ro.pricepage.model;

import org.primefaces.model.SelectableDataModel;
import ro.pricepage.persistence.entities.Producer;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 * User: radutoev
 * Date: 01.12.2012
 * Time: 20:59
 */
public class ProducerDataModel extends ListDataModel<Producer> implements SelectableDataModel<Producer>
{
    public ProducerDataModel(){}

    public ProducerDataModel(List<Producer> producers){
        super(producers);
    }

    @Override
    public Producer getRowData(String rowKey){
        List<Producer> producers = (List<Producer>) getWrappedData();
        if(rowKey != null && !rowKey.equals("null")){
            for(Producer producer : producers){
                if(producer.getId().compareTo(Integer.valueOf(rowKey)) == 0){
                    return producer;
                }
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(Producer producer){
        return producer.getId();
    }
}
