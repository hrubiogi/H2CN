public class PremiumCustomer extends Customer
{
    private static final float ShippingDisc = 0.2F;
    private static final float YearMemb = 9.9F;

    public static float getShippingDisc()
    {
        return ShippingDisc;
    }

    public static float getYearMemb()
    {
        return YearMemb;
    }

    public PremiumCustomer(String name, String address, String nif, String email)
    {
        super(name, address, nif, email);
    }


    @Override
    public String customerType()
    {
        return null;
        //TBD
    }

    @Override
    public float yearCalc()
    {
        return 0;
        //TBD
    }

    @Override
    public float shippingDisc()
    {
        return 0;
        //TBD
    }


}
