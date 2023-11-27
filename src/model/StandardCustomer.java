package model;

public class StandardCustomer extends Customer
{
    public StandardCustomer(String name, String address, String nif, String email) {
        super(name, address, nif, email);
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


}

