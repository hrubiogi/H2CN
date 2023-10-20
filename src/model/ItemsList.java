package model;

public class ItemsList extends List<Item>
{
    //constructor
    public ItemsList()
    {
        super();
    }

    //methods
    public void saveItem(String code, String description, float price, float shippingCost, int prepTime)
    {
        Item item = new Item(code, description, price, shippingCost, prepTime);
        add(item);
    }

    public void getItems()
    {
        System.out.println("\n[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[");
        System.out.println("LISTA DE PRODUCTOS: \n");
        System.out.println(getList() + "]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]\n");
    }
}
