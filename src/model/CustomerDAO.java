package model;

import java.sql.ResultSet;

public interface CustomerDAO {
    void savePremCustomer(String name, String nif, String adress, String email);
    void saveStdCustomer(String name, String nif, String adress, String email);
    void listCustomers();
}
