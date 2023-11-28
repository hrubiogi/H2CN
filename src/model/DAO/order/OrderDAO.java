package model.DAO.order;

import model.Customer;
import model.Item;
import model.Order;

import java.util.ArrayList;

public interface OrderDAO {

    public void saveOrder(Customer customer, Item item, int quantity);

    ArrayList<Order> listOrders();

    public void deleteOrder(String id);

    public ArrayList<Order> getPendingOrders();


    public ArrayList<Order> getPendingOrdersByCustomer(Customer customer);

    public ArrayList<Order> getSentOrdersByCustomer(Customer customer);

    public ArrayList<Order> getSentOrders();
}

