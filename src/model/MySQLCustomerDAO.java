package model;

import utils.ConnectDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MySQLCustomerDAO implements CustomerDAO {
    @Override
    public void savePremCustomer(String name, String address, String nif, String email) {
        try (Connection connection = ConnectDB.connect()) {
            if (connection != null) {
                System.out.println("Conexión exitosa");

                    String sql = "INSERT INTO customers_premium (name, address, nif, email) VALUES (?, ?, ?, ?)";
                
                // Crear la declaración preparada con los valores del pedido
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, name);
                    statement.setString(2, address);
                    statement.setString(3, nif);
                    statement.setString(4, email);

                    // Ejecutar la sentencia SQL
                    statement.executeUpdate();

                    // Confirmar la transacción (si es necesario)
                    //connection.commit();
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Revertir la transacción en caso de error (si es necesario)
                    connection.rollback();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveStdCustomer(String name, String address, String nif, String email) {
        try (Connection connection = ConnectDB.connect()) {
            if (connection != null) {
                System.out.println("Conexión exitosa");

                    String sql = "INSERT INTO customers_standard (name, address, nif, email) VALUES (?, ?, ?, ?)";
                
                // Crear la declaración preparada con los valores del pedido
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, name);
                    statement.setString(2, address);
                    statement.setString(3, nif);
                    statement.setString(4, email);

                    // Ejecutar la sentencia SQL
                    statement.executeUpdate();

                    // Confirmar la transacción (si es necesario)
                    //connection.commit();
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Revertir la transacción en caso de error (si es necesario)
                    connection.rollback();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Customer> listCustomers() {
        ArrayList<Customer> customerList = new ArrayList<Customer>();
        try (Connection connection = ConnectDB.connect()) {
            if (connection != null) {
                System.out.println("Conexión exitosa");

                // Sentencia SQL para seleccionar todos los Customers
                String sql = "SELECT name, address, nif, email, 'Premium' AS customer_type FROM customers_premium UNION SELECT name, address, nif, email, 'Standard' AS customer_type FROM customers_standard";

                // Crear la declaración preparada
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    // Ejecutar la consulta SELECT
                    try (ResultSet resultSet = statement.executeQuery()) {
                        // Procesar y mostrar los resultados
                        while (resultSet.next()) {
                            // Aquí puedes acceder a los valores de las columnas de cada fila
                            String name = resultSet.getString("name");
                            String address = resultSet.getString("address");
                            String nif = resultSet.getString("nif");
                            String email = resultSet.getString("email");
                            String tipoCliente = resultSet.getString("customer_type");

                            if (tipoCliente == "Premium") {
                                PremiumCustomer c = new PremiumCustomer(name, address, nif, email);
                                customerList.add(c);
                            } else {
                                StandardCustomer c = new StandardCustomer(name, address, nif, email);
                                customerList.add(c);
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }

    public ArrayList<Customer> listPremiumCustomers() {
        ArrayList<Customer> premiumCustomerList = new ArrayList<Customer>();
        try (Connection connection = ConnectDB.connect()) {
            if (connection != null) {
                System.out.println("Conexión exitosa");

                // Sentencia SQL para seleccionar todos los Customers
                String sql = "SELECT * FROM customers_premium";

                // Crear la declaración preparada
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    // Ejecutar la consulta SELECT
                    try (ResultSet resultSet = statement.executeQuery()) {
                        // Procesar y mostrar los resultados
                        while (resultSet.next()) {
                            // Aquí puedes acceder a los valores de las columnas de cada fila
                            String name = resultSet.getString("name");
                            String address = resultSet.getString("address");
                            String nif = resultSet.getString("nif");
                            String email = resultSet.getString("email");

                            PremiumCustomer c = new PremiumCustomer(name, address, nif, email);
                            premiumCustomerList.add(c);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return premiumCustomerList;
    }

    public ArrayList<Customer> listStdCustomers() {
        ArrayList<Customer> stdCustomerList = new ArrayList<Customer>();
        try (Connection connection = ConnectDB.connect()) {
            if (connection != null) {
                System.out.println("Conexión exitosa");

                // Sentencia SQL para seleccionar todos los Customers
                String sql = "SELECT * FROM customers_standard";

                // Crear la declaración preparada
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    // Ejecutar la consulta SELECT
                    try (ResultSet resultSet = statement.executeQuery()) {
                        // Procesar y mostrar los resultados
                        while (resultSet.next()) {
                            // Aquí puedes acceder a los valores de las columnas de cada fila
                            String name = resultSet.getString("name");
                            String address = resultSet.getString("address");
                            String nif = resultSet.getString("nif");
                            String email = resultSet.getString("email");

                            PremiumCustomer c = new PremiumCustomer(name, address, nif, email);
                            stdCustomerList.add(c);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stdCustomerList;
    }

}
