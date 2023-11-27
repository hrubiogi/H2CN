package model;

import utils.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO {
    void savePremCustomer(String name, String nif, String adress, String email);

    void saveStdCustomer(String name, String nif, String adress, String email);

    ArrayList<Customer> listCustomers();

    ArrayList<Customer> listPremiumCustomers();

    ArrayList<Customer> listStdCustomers();
}
