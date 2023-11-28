package model;

import model.DAO.DAOFactory;
import model.DAO.MySQLDAOFactory;

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

    public Item getItemByCode(String code) {
        ArrayList<Item> itemList = daoFactory.createItemDAO().listItems();
        for(Item item: itemList){
            if(item.getCode().equalsIgnoreCase(code)){
                return item;
            }
        }
        return null;
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

    public Customer getCustomerByEmail(String email) {
        ArrayList<Customer> customerList = daoFactory.createCustomerDAO().listCustomers();
        for(Customer customer: customerList){
            if(customer.getEmail().equalsIgnoreCase(email)){
                return customer;
            }
        }
        return null;
    }

    public ArrayList<Customer> getPremiumCustomerList() {
        ArrayList<Customer> customerList = daoFactory.createCustomerDAO().listPremiumCustomers();
        return customerList;
    }

    public ArrayList<Customer> getStdCustomerList() {
        ArrayList<Customer> customerList = daoFactory.createCustomerDAO().listStdCustomers();
        return customerList;
    }

    // Order methods
    public void saveOrder(Customer customer, Item item, int quantity){
        daoFactory.createOrderDAO().saveOrder(customer, item, quantity);
    }

    public ArrayList<Order> getOrdersListDAO(){
        ArrayList<Order> orderList = daoFactory.createOrderDAO().listOrders();
        return orderList;
    }

    public ArrayList<Order> getPendingOrdersListDAO(){
        ArrayList<Order> pendingOrderList = daoFactory.createOrderDAO().getPendingOrders();
        return pendingOrderList;
    }

    public ArrayList<Order> getPendingOrdersByCustomerListDAO(Customer c){
        ArrayList<Order> pendingOrderByCustomerList = daoFactory.createOrderDAO().getPendingOrdersByCustomer(c);
        return pendingOrderByCustomerList;
    }

    public ArrayList<Order> getSentOrdersListDAO(){
        ArrayList<Order> sentOrderList = daoFactory.createOrderDAO().getSentOrders();
        return sentOrderList;
    }

    public ArrayList<Order> getSentOrdersByCustomerListDAO(Customer c){
        ArrayList<Order> sentOrderByCustomerList = daoFactory.createOrderDAO().getSentOrdersByCustomer(c);
        return sentOrderByCustomerList;
    }

    public void deleteOrder(String id) {
        daoFactory.createOrderDAO().deleteOrder(id);
    }
}
