package model.DAO;

import model.DAO.customer.CustomerDAO;
import model.DAO.customer.MySQLCustomerDAO;
import model.DAO.customer.MySQLCustomerJpaDAO;
import model.DAO.item.ItemDAO;
import model.DAO.item.MySQLItemDAO;
import model.DAO.item.MySQLItemJpaDAO;
import model.DAO.order.MySQLOrderDAO;
import model.DAO.order.MySQLOrderJpaDAO;
import model.DAO.order.OrderDAO;

public class MySQLDAOFactory implements DAOFactory {

    @Override
    public ItemDAO createItemDAO() {
        return new MySQLItemJpaDAO();
    }

    @Override
    public CustomerDAO createCustomerDAO() {
        return new MySQLCustomerJpaDAO();
    }

    @Override
    public OrderDAO createOrderDAO(){
        return new MySQLOrderJpaDAO();
    }

}
