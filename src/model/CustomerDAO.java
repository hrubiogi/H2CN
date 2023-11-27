package model;

import java.sql.ResultSet;

public interface CustomerDAO {
    void saveCustomer(String name, String nif, String adress, String email);
    void listCustomers();
}
