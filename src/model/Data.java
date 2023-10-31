package model;

public class Data
{
    private ItemsList itemsList;
    private CustomersList customersList;
    private OrdersList ordersList;

    public ItemsList getItemsList()
    {
        return itemsList;
    }
    public CustomersList getCustomersList()
    {
        return customersList;
    }
    public OrdersList getOrdersList()
    {
        return ordersList;
    }

    //constructor
    public Data ()
    {
        itemsList = new ItemsList();
        customersList = new CustomersList();
        ordersList = new OrdersList();
    }
}
