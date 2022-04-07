package models;

public class Airline {
    private String code; // COIT111134
    private String name;

    public Airline() throws AirlineException
    {
        this ("unknown", "unknown"); // Call Parameterised Constructor
    }

    public Airline(String code, String name) throws AirlineException
    {
        if (code.length() == 0)
            throw new AirlineException ("Error: code cannot be blank.");
        else if (name.length() == 0)
            throw new AirlineException ("Error: name cannot be blank.");

        this.code = code;
        this.name = name;
    }

    public String getCode()
    {
        return code;
    }

    public String getName()
    {
        return name;
    }


    public void setCode(String code) throws AirlineException
    {
        if (code.length() == 0)
            throw new AirlineException ("Error: code cannot be blank.");

        this.code = code;
    }

    public void setName(String name) throws AirlineException
    {
        if (name.length() == 0)
            throw new AirlineException ("Error: name cannot be blank.");

        this.name = name;
    }


    @Override
    public String toString()
    {
        return  String.format ("%-10s", code)  +
                String.format ("%-20s", name);
    }
}
