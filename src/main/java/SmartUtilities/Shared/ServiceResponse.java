package SmartUtilities.Shared;

public class ServiceResponse<T> {
    
    private String title;

    private String type;

    private T required;

    
    public ServiceResponse() {
    }

    public ServiceResponse(String title, String type, T required)
    {
        this.title = title;
        this.type = type;
        this.required = required;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getType()
    {
        return this.type;
    }

    public T getRequired()
    {
        return this.required;
    }

    public void setTitle( String title)
    {
        this.title = title;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setRequired(T required)
    {
        this.required = required;
    }
}
