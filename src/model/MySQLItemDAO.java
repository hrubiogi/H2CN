package model;

import utils.ConnectDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySQLItemDAO implements ItemDAO {
    @Override
    public void saveItem(String code, String description, float price, float shippingCost, int prepTime) {
        try (Connection connection = ConnectDB.connect()) {
            if (connection != null) {
                System.out.println("Conexión exitosa");

                // Sentencia SQL para insertar un nuevo item
                String sql = "INSERT INTO items (code, description, price, shippingCost, prepTime) VALUES (?, ?, ?, ?, ?)";

                // Crear la declaración preparada con los valores del pedido
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, code);
                    statement.setString(2, description);
                    statement.setFloat(3, price);
                    statement.setFloat(4, shippingCost);
                    statement.setInt(5, prepTime);

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
    public void listItems() {
        try (Connection connection = ConnectDB.connect()) {
            if (connection != null) {
                System.out.println("Conexión exitosa");

                // Sentencia SQL para seleccionar todos los items
                String sql = "SELECT * FROM items";

                // Crear la declaración preparada
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    // Ejecutar la consulta SELECT
                    try (ResultSet resultSet = statement.executeQuery()) {
                        System.out.println("\n<------------Lista de Artículos------------->");
                        // Procesar y mostrar los resultados
                        while (resultSet.next()) {
                            // Aquí puedes acceder a los valores de las columnas de cada fila
                            String code = resultSet.getString("code");
                            String description = resultSet.getString("description");
                            float price = resultSet.getFloat("price");
                            float shippingCost = resultSet.getFloat("shippingCost");
                            int prepTime = resultSet.getInt("prepTime");

                            // Mostrar los resultados en la consola
                            System.out.println("Code: " + code);
                            System.out.println("Description: " + description);
                            System.out.println("Price: " + price);
                            System.out.println("Shipping Cost: " + shippingCost);
                            System.out.println("Preparation Time: " + prepTime);
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
