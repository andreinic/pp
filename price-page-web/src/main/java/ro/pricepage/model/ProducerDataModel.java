package ro.pricepage.model;

import org.primefaces.model.SelectableDataModel;
import org.primefaces.model.SortOrder;
import ro.pricepage.persistence.entities.Producer;

import javax.faces.model.ListDataModel;
import java.util.List;
import java.util.Map;

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

    //TODO Implement here
//    @Override
    public List<Producer> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters){
        return null;
    }
}
