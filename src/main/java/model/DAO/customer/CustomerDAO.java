package model.DAO.customer;

import model.Customer;
import utils.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO {

    void saveCustomer(Customer customer);

    List<Customer> listCustomers();

    Customer getCustomerByEmail(String email);

    List<Customer> listPremiumCustomers();

    List<Customer> listStdCustomers();
}
