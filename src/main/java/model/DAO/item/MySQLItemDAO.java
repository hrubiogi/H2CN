package model.DAO.item;

import model.DAO.item.ItemDAO;
import model.Item;
import utils.ConnectDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MySQLItemDAO {

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


    public ArrayList<Item> listItems() {
        ArrayList<Item> itemList = new ArrayList<>();
        try (Connection connection = ConnectDB.connect()) {
            if (connection != null) {
                System.out.println("Conexión exitosa");

                // Sentencia SQL para seleccionar todos los items
                String sql = "SELECT * FROM items";

                // Crear la declaración preparada
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    // Ejecutar la consulta SELECT
                    try (ResultSet resultSet = statement.executeQuery()) {

                        // Procesar y mostrar los resultados
                        while (resultSet.next()) {
                            Item item = new Item(
                                    resultSet.getString("code"),
                                    resultSet.getString("description"),
                                    resultSet.getFloat("price"),
                                    resultSet.getFloat("shippingCost"),
                                    resultSet.getInt("prepTime")
                            );
                            itemList.add(item);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemList;
    }
}
