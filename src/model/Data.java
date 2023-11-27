package model;

import java.util.ArrayList;

public class Data
{
    private ItemsList itemsList;
    private CustomersList customersList;
    private OrdersList ordersList;

    private DAOFactory daoFactory;

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

    public DAOFactory getDaoFactory() {
        return daoFactory;
    }

    public ArrayList<Item> getItemListDAO() {
        ArrayList<Item> itemList = daoFactory.createItemDAO().listItems();
        return itemList;
    }

    //constructor
    public Data ()
    {
        //itemsList = new ItemsList();
        //customersList = new CustomersList();
        //ordersList = new OrdersList();
        daoFactory = new MySQLDAOFactory();
    }
}
