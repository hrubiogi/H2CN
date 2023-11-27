package model;

public interface DAOFactory {
    ItemDAO createItemDAO();
    CustomerDAO createCustomerDAO();
}