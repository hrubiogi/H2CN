package model;

public class Item
{
    //Fields
    private String code;
    private String description;
    private float price;
    private float shippingCost;
    private int prepTime;


    //getters y setters
    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public float getPrice()
    {
        return price;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }

    public float getShippingCost()
    {
        return shippingCost;
    }

    public void setShippingCost(float shippingCost)
    {
        this.shippingCost = shippingCost;
    }

    public int getPrepTime()
    {
        return prepTime;
    }

    public void setPrepTime(int prepTime)
    {
        this.prepTime = prepTime;
    }


    //constructor
    public Item(String code, String description, float price, float shippingCost, int prepTime)
    {
        this.code = code;
        this.description = description;
        this.price = price;
        this.shippingCost = shippingCost;
        this.prepTime = prepTime;
    }


    //methods
    @Override
    public String toString()
    {
        return "<------------Item------------->" + '\n' +
                "code: " + code + '\n' +
                "description: " + description + '\n' +
                "price: " + price + '\n' +
                "shippingCost: " + shippingCost + '\n' +
                "prepTime: " + prepTime + '\n' +
                " ----------------------------";
    }
}
