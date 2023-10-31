package model;

public abstract class Customer {
    private String name;
    private String address;
    private String nif;
    private String email;

    public Customer(String name, String address, String nif, String email)
    {
        this.name = name;
        this.address = address;
        this.nif = nif;
        this.email = email;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getNif()
    {
        return nif;
    }

    public void setNif(String nif)
    {
        this.nif = nif;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public abstract String customerType ();

    public abstract float yearCalc();

    public abstract float shippingDisc();



    @Override
    public String toString()
    {
        return "model.Customer{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", nif='" + nif + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
