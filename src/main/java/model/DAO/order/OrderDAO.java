package model.DAO.order;

import model.Customer;
import model.Item;
import model.Order;

import java.util.List;

public interface OrderDAO {

    void saveOrder(Order order);

    List<Order> getOrders();

    List<Order> getPendingOrders();

    List<Order> getPendingOrdersByCustomer(Customer customer);

    List<Order> getSentOrdersByCustomer(Customer customer);

    List<Order> getSentOrders();

    Order getOrderById(String code);

    void deleteOrder(String id);
}

