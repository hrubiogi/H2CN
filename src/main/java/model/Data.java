package model;

import model.DAO.DAOFactory;
import model.DAO.MySQLDAOFactory;
import utils.DuplicateOrderIdException;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

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
        Item newItem = new Item(code, description, price, shippingCost, prepTime);
        daoFactory.createItemDAO().saveItem(newItem);
    }

    public List<Item> getItemList() {
        List<Item> itemList = daoFactory.createItemDAO().listItems();
        return itemList;
    }

    public Item getItemByCode(String code) {
        Item item = daoFactory.createItemDAO().getItem(code);
        return item;
    }

    // Customer methods
    public void saveCustomer(String name, String address, String nif, String email, boolean isPremium) {
        Customer customer;
        if (isPremium) {
            customer = new PremiumCustomer(name, address, nif, email);
        } else {
            customer = new StandardCustomer(name, address, nif, email);
        }
        daoFactory.createCustomerDAO().saveCustomer(customer);
    }

    public List<Customer> getCustomerList() {
        List<Customer> customerList = daoFactory.createCustomerDAO().listCustomers();
        return customerList;
    }

    public Customer getCustomerByEmail(String email) {
        Customer customer = daoFactory.createCustomerDAO().getCustomerByEmail(email);
        return customer;
    }

    public List<Customer> getPremiumCustomerList() {
        List<Customer> customerList = daoFactory.createCustomerDAO().listPremiumCustomers();
        return customerList;
    }

    public List<Customer> getStdCustomerList() {
        List<Customer> customerList = daoFactory.createCustomerDAO().listStdCustomers();
        return customerList;
    }

    // Order methods
    public void saveOrder(Customer customer, Item item, int quantity) throws DuplicateOrderIdException {
        Order newOrder = new Order(customer, item, quantity);
        daoFactory.createOrderDAO().saveOrder(newOrder);
    }

    public List<Order> getOrdersList(){
        List<Order> orderList = daoFactory.createOrderDAO().getOrders();
        return orderList;
    }

    public boolean orderIsSent(String id) {
        Order order = daoFactory.createOrderDAO().getOrderById(id);
        return order.orderIsSent();
    }

    public List<Order> getPendingOrdersList(){
        List<Order> pendingOrderList = daoFactory.createOrderDAO().getPendingOrders();
        return pendingOrderList;
    }

    public List<Order> getPendingOrdersByCustomerList(Customer c){
        List<Order> pendingOrderByCustomerList = daoFactory.createOrderDAO().getPendingOrdersByCustomer(c);
        return pendingOrderByCustomerList;
    }

    public List<Order> getSentOrdersList(){
        List<Order> sentOrderList = daoFactory.createOrderDAO().getSentOrders();
        return sentOrderList;
    }

    public List<Order> getSentOrdersByCustomerList(Customer c){
        List<Order> sentOrderByCustomerList = daoFactory.createOrderDAO().getSentOrdersByCustomer(c);
        return sentOrderByCustomerList;
    }

    public void deleteOrder(String id) {
        daoFactory.createOrderDAO().deleteOrder(id);
    }
}
