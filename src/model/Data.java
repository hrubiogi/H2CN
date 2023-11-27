package model;

import java.util.ArrayList;

public class Data
{

    private DAOFactory daoFactory;

    public Data ()
    {
        daoFactory = new MySQLDAOFactory();
    }

    public DAOFactory getDaoFactory() {
        return daoFactory;
    }

    // Item methods
    public void saveItem(String code, String description, float price, float shippingCost, int prepTime) {
        daoFactory.createItemDAO().saveItem(code, description, price, shippingCost, prepTime);
    }

    public ArrayList<Item> getItemListDAO() {
        ArrayList<Item> itemList = daoFactory.createItemDAO().listItems();
        return itemList;
    }

    // Customer methods
    public void saveCustomer(String name, String address, String nif, String email, boolean isPremium) {
        if (isPremium) {
            daoFactory.createCustomerDAO().savePremCustomer(name, address, nif, email);
        } else {
            daoFactory.createCustomerDAO().saveStdCustomer(name, address, nif, email);
        }

    }
    public ArrayList<Customer> getCustomerList() {
        ArrayList<Customer> customerList = daoFactory.createCustomerDAO().listCustomers();
        return customerList;
    }

    public ArrayList<Customer> getPremiumCustomerList() {
        ArrayList<Customer> customerList = daoFactory.createCustomerDAO().listPremiumCustomers();
        return customerList;
    }

    public ArrayList<Customer> getStdCustomerList() {
        ArrayList<Customer> customerList = daoFactory.createCustomerDAO().listStdCustomers();
        return customerList;
    }
}
