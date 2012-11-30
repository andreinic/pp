package ro.pricepage.utils;

/**
 * User: radutoev
 * Date: 29.11.2012
 * Time: 23:36
 */
public class SessionObject
{
    private String name;

    public String getName(){ return name; }
    public void setName(String value){ name = value; }

    @Override
    public String toString(){
        return name;
    }
}
