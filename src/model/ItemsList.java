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
        try {
            Item item = new Item(code, description, price, shippingCost, prepTime);
            add(item);
        } catch (IllegalArgumentException e) {
            System.out.print("Tipo de dato no valido: " + e.getMessage());
        }
    }

    public void getItems()
    {
        System.out.println(getList());
    }

    public Item getItemByCode(String code){
        for (Item item : getList()){
            if (item.getCode().equals(code))
            {
                return item;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "ItemList{" +
                "list=" + list +
                "} " + super.toString();
    }
}
