package model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("1")
public class StandardCustomer extends Customer
{
    public StandardCustomer(String name, String address, String nif, String email) {
        super(name, address, nif, email);
    }

    public StandardCustomer() {
    }

    @Override
    public String customerType() {
        return "S";
        //TBD
    }

    @Override
    public float yearCalc() {
        return 0;
        //TBD
    }

    @Override
    public float shippingDisc() {
        return 0;
        //TBD
    }

    public String toString()
    {
        return super.toString() +
                "Tipo: Estándar \n";
    }

}

