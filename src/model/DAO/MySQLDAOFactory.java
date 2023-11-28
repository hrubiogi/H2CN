package model.DAO;

import model.DAO.customer.CustomerDAO;
import model.DAO.customer.MySQLCustomerDAO;
import model.DAO.item.ItemDAO;
import model.DAO.item.MySQLItemDAO;
import model.DAO.order.OrderDAO;

public class MySQLDAOFactory implements DAOFactory {

    @Override
    public ItemDAO createItemDAO() {
        return new MySQLItemDAO();
    }

    @Override
    public CustomerDAO createCustomerDAO() {
        return new MySQLCustomerDAO();
    }

    @Override
    public OrderDAO createOrderDAO(){
        return new MySQLOrderDAO();
    }

}
