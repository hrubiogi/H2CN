package model;

public interface DAOFactory {
    ItemDAO createItemDAO();
    CustomerDAO createCustomerDAO();
    OrderDAO createOrderDAO();


}