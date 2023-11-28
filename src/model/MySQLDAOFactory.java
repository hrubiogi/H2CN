package model;

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
