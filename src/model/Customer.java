package model;

public abstract class Customer
{
    private String name;
    private String address;
    private String nif;
    private String email;

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

    public Customer(String name, String address, String nif, String email) throws CustomerException
    {
        if(name == null){

            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío");
        }
        if(nif == null){

            throw new IllegalArgumentException("El NIF no puede estar vacío");
        }
        if(email == null){

            throw new IllegalArgumentException("El Email no puede estar vacío");
        }

        this.name = name;
        this.address = address;
        this.nif = nif;
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
