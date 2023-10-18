package model;

import java.time.LocalDate;
import java.util.Objects;

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



    //methods
    public void saveItem(String code, String description, float price, float shippingCost, int prepTime)
    {
        Item item = new Item(code, description, price, shippingCost, prepTime);
        getItemsList().add(item);
    }

    public void getItems()
    {
        System.out.println(getItemsList().getList());
    }

    public void saveStdCustomer(String name, String address, String nif, String email)
    {
        Customer standardCustomer = new StandardCustomer(name, address, nif, email);
        getCustomersList().add(standardCustomer);
    }

    public void savePremCustomer(String name, String address, String nif, String email)
    {
        Customer premiumCustomer = new PremiumCustomer(name, address, nif, email);
        getCustomersList().add(premiumCustomer);
    }

    public void getCustomers()
    {
        System.out.println(getCustomersList());
    }

    public void getStdCustomers()
    {
        //TBD
    }

    public void getPremCustomers()
    {
        //TBD
    }

    public void saveOrder(int id, Customer customer, Item item, int quantity, LocalDate date)
    {
        Order order = new Order(id, customer, item, quantity, date);
        getOrdersList().add(order);
    }

    public void deleteOrder(int id)
    {
        for (Order order : getOrdersList().getList())
        {
            if (Objects.equals(id, order.getId()))
            {
                getOrdersList().delete(order);
            }
        }
    }

    public void getPendingOrders()
    {
        //TBD
    }

    public void getSentOrders()
    {
        //TBD
    }



}
