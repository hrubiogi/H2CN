package model;

public class MySQLDAOFactory implements DAOFactory {

    @Override
    public ItemDAO createItemDAO() {
        return new MySQLItemDAO();
    }

    /**
     * @return
     */
    @Override
    public CustomerDAO createCustomerDAO() {
        return new MySQLCustomerDAO();
    }
}
