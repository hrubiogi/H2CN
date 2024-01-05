package model;

import javax.persistence.*;

@Entity
@Table(name = "customers")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.INTEGER)
public abstract class Customer {
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "nif")
    private String nif;
    @Id
    private String email;

    public Customer(String name, String address, String nif, String email)
    {
        this.name = name;
        this.address = address;
        this.nif = nif;
        this.email = email;
    }

    public Customer() {

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
        return "<------------Customer------------->" + '\n' +
                "name: " + name + '\n' +
                "address: " + address + '\n' +
                "nif: " + nif + '\n' +
                "email: " + email + '\n';
    }
}
