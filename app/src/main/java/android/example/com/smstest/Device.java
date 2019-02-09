package android.example.com.smstest;

public class Device
{

    private String id;
    private String name;

    public Device(String id, String name)
    {

        this.id = id;
        this.name = name;

    }


    public Device(){}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name + "  " + id;
    }
}
