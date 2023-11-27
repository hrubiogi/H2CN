package model;

import utils.ConnectDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySQLCustomerDAO implements CustomerDAO {
    @Override
    public void savePremCustomer(String name, String address, String nif, String email) {
        try (Connection connection = ConnectDB.connect()) {
            if (connection != null) {
                System.out.println("Conexión exitosa");

                    String sql = "INSERT INTO Customers_premium (name, address, nif, email) VALUES (?, ?, ?, ?)";
                
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

                    String sql = "INSERT INTO Customers_standard (name, address, nif, email) VALUES (?, ?, ?, ?)";
                
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
    public void listCustomers() {
        try (Connection connection = ConnectDB.connect()) {
            if (connection != null) {
                System.out.println("Conexión exitosa");

                // Sentencia SQL para seleccionar todos los Customers
                String sql = "SELECT * FROM Customers_premium p JOIN Customers_standard s ON p.nif = s.nif;";

                // Crear la declaración preparada
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    // Ejecutar la consulta SELECT
                    try (ResultSet resultSet = statement.executeQuery()) {
                        System.out.println("\n<------------Lista de Clientes------------->");
                        // Procesar y mostrar los resultados
                        while (resultSet.next()) {
                            // Aquí puedes acceder a los valores de las columnas de cada fila
                            String name = resultSet.getString("name");
                            String address = resultSet.getString("address");
                            String nif = resultSet.getString("nif");
                            String email = resultSet.getString("email");

                            // Mostrar los resultados en la consola
                            System.out.println("Name: " + name);
                            System.out.println("Address: " + address);
                            System.out.println("Nif: " + nif);
                            System.out.println("Email: " + email);
                            System.out.println("---------------------------------------------");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
