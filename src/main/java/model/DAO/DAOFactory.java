package model.DAO;

import model.DAO.customer.CustomerDAO;
import model.DAO.item.ItemDAO;
import model.DAO.order.OrderDAO;

public interface DAOFactory {
    ItemDAO createItemDAO();
    CustomerDAO createCustomerDAO();
    OrderDAO createOrderDAO();


}