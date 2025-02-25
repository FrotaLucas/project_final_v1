package SmartUtilities.Shared;

import java.util.List;
import java.util.Map;

public class ServiceResponse<T> {
    
    private String title;

    private String type;

    private String required; //PRECISA SER UM ARRAY DE STRING!! OLHAR JSON modelo

    private Map<String, Object> properties;

    public ServiceResponse()
    {

    }
    
    public ServiceResponse(String title, String type, String required, Map<String, Object> properties)
    {
        this.title = title;
        this.type = type;
        this.required = required;
        this.properties = properties;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getType()
    {
        return this.type;
    }

    public String getRequired()
    {
        return this.required;
    }

    public Map<String, Object> getProperties()
    {
        return this.properties;
    }

    public void setTitle( String title)
    {
        this.title = title;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setRequired(String required)
    {
        this.required = required;
    }

    public void setProperties(Map<String, Object> properties)
    {
        this.properties = properties;
    }
}
