package model.DAO.order;

import model.*;
import utils.ConnectDB;
import utils.DuplicateOrderIdException;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Objects;

public class MySQLOrderDAO {

    public void saveOrder(Customer customer, Item item, Order order) {
        Connection connection = null;
        try {
            connection = ConnectDB.connect();
            if (connection != null) {

                // Iniciar la transacción
                connection.setAutoCommit(false);

                String sql = "INSERT INTO orders (id, date, quantity, email, itemCode) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, order.getId());
                    preparedStatement.setTimestamp(2, Timestamp.valueOf(order.getDate()));
                    preparedStatement.setInt(3, order.getQuantity());
                    preparedStatement.setString(4, customer.getEmail());
                    preparedStatement.setString(5, item.getCode());
                    preparedStatement.executeUpdate();

                    // Confirmar la transacción
                    connection.commit();
                } catch (SQLException ex) {
                    // Revertir la transacción en caso de error
                    connection.rollback();
                    throw new RuntimeException("Error al insertar el pedido", ex);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error de conexión", e);
        } finally {
            // Cerrar la conexión
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);  // Restaurar el modo de autoconfirmación
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public ArrayList<Order> listOrders() {
        ArrayList<Order> ordersList = new ArrayList<>();
        try (Connection connection = ConnectDB.connect()) {
            if (connection != null) {

                String sql = "SELECT " +
                        "o.id AS order_id" +
                        "c.nif AS customer_nif" +
                        "c.name AS customer_name" +
                        "c.type AS customer_type" +
                        "i.code AS item_code" +
                        "i.description AS item_description" +
                        "i.price AS item_price" +
                        "o.quantity AS quantity" +
                        "i.shippingCost AS shipping_cost" +
                        "(i.price * o.quantity + i.shippingCost) AS full_price" +
                        "o.date AS order_date" +
                        "(UNIX_TIMESTAMP() + (i.prepTime * 60)) > o.date AS is_sent" +
                "FROM  orders o"+
                        "JOIN customers c ON o.email = c.email" +
                        "JOIN items i ON o.itemCode = i.code ";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            int customer_Type = resultSet.getInt("customer_type");
                            PremiumCustomer customer_p = null;
                            StandardCustomer customer_s = null;

                            if (Objects.equals(customer_Type, 2)) {
                                customer_p = new PremiumCustomer(
                                        resultSet.getString("name"),
                                        resultSet.getString("customerAddress"),
                                        resultSet.getString("customerNif"),
                                        resultSet.getString("customer_email")
                                );
                            } else {
                                customer_s = new StandardCustomer(
                                        resultSet.getString("name"),
                                        resultSet.getString("customerAddress"),
                                        resultSet.getString("customerNif"),
                                        resultSet.getString("customer_email")
                                );
                            }

                            Item item = new Item(
                                    resultSet.getString("item_code"),
                                    resultSet.getString("item_description"),
                                    resultSet.getFloat("item_price"),
                                    resultSet.getFloat("item_shipping_cost"),
                                    resultSet.getInt("item_prep_time")
                            );

                            Order order = new Order((customer_p != null) ? customer_p : customer_s, item, resultSet.getInt("quantity"), resultSet.getString("order_id"), resultSet.getDate("order_date").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

                            ordersList.add(order);
                        }
                    } catch (DuplicateOrderIdException e) {
                        throw new RuntimeException(e);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ordersList;
    }


    public void deleteOrder(String orderId) {
        try (Connection connection = ConnectDB.connect()) {
            if (connection != null) {

                // Declaración SQL
                String sql = "DELETE orders FROM orders " +
                        "JOIN items ON items.code = orders.itemCode " +
                        "WHERE orders.id = ? AND (NOW() > DATE_ADD(orders.date, INTERVAL items.prepTime MINUTE))";

                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    //Establecer el valor del parámetro
                    preparedStatement.setString(1, orderId);

                    // Ejecutar la declaración SQL
                    int rowsUpdated = preparedStatement.executeUpdate();

                    // Verificar si la eliminación fue exitosa
                    if (rowsUpdated > 0) {
                        System.out.println("Pedido eliminado con éxito");
                    } else {
                        System.out.println("No se encontró el pedido con ese ID");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Order> getPendingOrders(){
        ArrayList<Order> ordersList = new ArrayList<>();
        try (Connection connection = ConnectDB.connect()) {

            String sql = "SELECT " +
                    "o.id AS order_id, " +
                    "c.nif AS customer_nif, " +
                    "c.name AS customer_name, " +
                    "c.address AS customer_address, " +
                    "c.email AS customer_email, " +
                    "c.type AS customer_type, " +
                    "i.code AS item_code, " +
                    "i.description AS item_description, " +
                    "i.price AS item_price, " +
                    "i.shippingCost AS item_shipping_cost, " +
                    "i.prepTime AS item_prep_time, " +
                    "o.quantity AS quantity, " +
                    "(i.price * o.quantity + i.shippingCost) AS full_price, " +
                    "o.date AS order_date " +
                    "FROM orders o " +
                    "JOIN customers c ON o.email = c.email " +
                    "JOIN items i ON o.itemCode = i.code " +
                    "WHERE (NOW() < DATE_ADD(o.date, INTERVAL i.prepTime MINUTE))";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int customer_Type = resultSet.getInt("customer_type");
                    PremiumCustomer customer_p = null;
                    StandardCustomer customer_s = null;

                    if (Objects.equals(customer_Type, 2)) {
                        customer_p = new PremiumCustomer(
                                resultSet.getString("customer_name"),
                                resultSet.getString("customer_address"),
                                resultSet.getString("customer_nif"),
                                resultSet.getString("customer_email")
                        );
                    } else {
                        customer_s = new StandardCustomer(
                                resultSet.getString("customer_name"),
                                resultSet.getString("customer_address"),
                                resultSet.getString("customer_nif"),
                                resultSet.getString("customer_email")
                        );
                    }


                    Item item = new Item(
                            resultSet.getString("item_code"),
                            resultSet.getString("item_description"),
                            resultSet.getFloat("item_price"),
                            resultSet.getFloat("item_shipping_cost"),
                            resultSet.getInt("item_prep_time")
                    );

//                    Timestamp time = new Timestamp(resultSet.getLong("order_date"));

                    Order order = new Order((customer_p != null) ? customer_p : customer_s, item, resultSet.getInt("quantity"), resultSet.getString("order_id"), resultSet.getTimestamp("order_date").toLocalDateTime());

                    ordersList.add(order);
                }

            } catch (DuplicateOrderIdException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ordersList;
    }


    public ArrayList<Order> getPendingOrdersByCustomer(Customer customer) {
        ArrayList<Order> ordersList = new ArrayList<>();
        try (Connection connection = ConnectDB.connect()) {

            String sql = "SELECT " +
                    "o.id AS order_id, " +
                    "c.nif AS customer_nif, " +
                    "c.name AS customer_name, " +
                    "c.address AS customer_address, " +
                    "c.email AS customer_email, " +
                    "c.type AS customer_type, " +
                    "i.code AS item_code, " +
                    "i.description AS item_description, " +
                    "i.price AS item_price, " +
                    "i.shippingCost AS item_shipping_cost, " +
                    "i.prepTime AS item_prep_time, " +
                    "o.quantity AS quantity, " +
                    "(i.price * o.quantity + i.shippingCost) AS full_price, " +
                    "o.date AS order_date " +
                    "FROM orders o " +
                    "JOIN customers c ON o.email = c.email " +
                    "JOIN items i ON o.itemCode = i.code " +
                    "WHERE (NOW() < DATE_ADD(o.date, INTERVAL i.prepTime MINUTE)) " +
                    "AND c.email = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, customer.getEmail());
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int customer_Type = resultSet.getInt("customer_type");
                    PremiumCustomer customer_p = null;
                    StandardCustomer customer_s = null;

                    if (Objects.equals(customer_Type, 2)) {
                        customer_p = new PremiumCustomer(
                                resultSet.getString("customer_name"),
                                resultSet.getString("customer_address"),
                                resultSet.getString("customer_nif"),
                                resultSet.getString("customer_email")
                        );
                    } else {
                        customer_s = new StandardCustomer(
                                resultSet.getString("customer_name"),
                                resultSet.getString("customer_address"),
                                resultSet.getString("customer_nif"),
                                resultSet.getString("customer_email")
                        );
                    }


                    Item item = new Item(
                            resultSet.getString("item_code"),
                            resultSet.getString("item_description"),
                            resultSet.getFloat("item_price"),
                            resultSet.getFloat("item_shipping_cost"),
                            resultSet.getInt("item_prep_time")
                    );

                    Order order = new Order((customer_p != null) ? customer_p : customer_s, item, resultSet.getInt("quantity"), resultSet.getString("order_id"), resultSet.getTimestamp("order_date").toLocalDateTime());

                    ordersList.add(order);
                }

            } catch (DuplicateOrderIdException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ordersList;
    }


    public ArrayList<Order> getSentOrdersByCustomer(Customer customer) {
        ArrayList<Order> ordersList = new ArrayList<>();
        try (Connection connection = ConnectDB.connect()) {

            String sql = "SELECT " +
                    "o.id AS order_id, " +
                    "c.nif AS customer_nif, " +
                    "c.name AS customer_name, " +
                    "c.address AS customer_address, " +
                    "c.email AS customer_email, " +
                    "c.type AS customer_type, " +
                    "i.code AS item_code, " +
                    "i.description AS item_description, " +
                    "i.price AS item_price, " +
                    "i.shippingCost AS item_shipping_cost, " +
                    "i.prepTime AS item_prep_time, " +
                    "o.quantity AS quantity, " +
                    "(i.price * o.quantity + i.shippingCost) AS full_price, " +
                    "o.date AS order_date " +
                    "FROM orders o " +
                    "JOIN customers c ON o.email = c.email " +
                    "JOIN items i ON o.itemCode = i.code " +
                    "WHERE (NOW() > DATE_ADD(o.date, INTERVAL i.prepTime MINUTE))" +
                    "AND c.email = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, customer.getEmail());
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int customer_Type = resultSet.getInt("customer_type");
                    PremiumCustomer customer_p = null;
                    StandardCustomer customer_s = null;

                    if (Objects.equals(customer_Type, 2)) {
                        customer_p = new PremiumCustomer(
                                resultSet.getString("customer_name"),
                                resultSet.getString("customer_address"),
                                resultSet.getString("customer_nif"),
                                resultSet.getString("customer_email")
                        );
                    } else {
                        customer_s = new StandardCustomer(
                                resultSet.getString("customer_name"),
                                resultSet.getString("customer_address"),
                                resultSet.getString("customer_nif"),
                                resultSet.getString("customer_email")
                        );
                    }
                    Item item = new Item(
                            resultSet.getString("item_code"),
                            resultSet.getString("item_description"),
                            resultSet.getFloat("item_price"),
                            resultSet.getFloat("item_shipping_cost"),
                            resultSet.getInt("item_prep_time")
                    );
                    Order order = new Order((customer_p != null) ? customer_p : customer_s, item, resultSet.getInt("quantity"), resultSet.getString("order_id"), resultSet.getTimestamp("order_date").toLocalDateTime());

                    ordersList.add(order);
                }
            }
        } catch (DuplicateOrderIdException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ordersList;
    }


    public ArrayList<Order> getSentOrders() {
        ArrayList<Order> ordersList = new ArrayList<>();
        try (Connection connection = ConnectDB.connect()) {
            String sql = "SELECT " +
                    "o.id AS order_id, " +
                    "c.nif AS customer_nif, " +
                    "c.name AS customer_name, " +
                    "c.address AS customer_address, " +
                    "c.email AS customer_email, " +
                    "c.type AS customer_type, " +
                    "i.code AS item_code, " +
                    "i.description AS item_description, " +
                    "i.price AS item_price, " +
                    "i.shippingCost AS item_shipping_cost, " +
                    "i.prepTime AS item_prep_time, " +
                    "o.quantity AS quantity, " +
                    "(i.price * o.quantity + i.shippingCost) AS full_price, " +
                    "o.date AS order_date " +
                    "FROM orders o " +
                    "JOIN customers c ON o.email = c.email " +
                    "JOIN items i ON o.itemCode = i.code " +
                    "WHERE (NOW() > DATE_ADD(o.date, INTERVAL i.prepTime MINUTE))";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int customer_Type = resultSet.getInt("customer_type");
                    PremiumCustomer customer_p = null;
                    StandardCustomer customer_s = null;

                    if (Objects.equals(customer_Type, 2)) {
                        customer_p = new PremiumCustomer(
                                resultSet.getString("customer_name"),
                                resultSet.getString("customer_address"),
                                resultSet.getString("customer_nif"),
                                resultSet.getString("customer_email")
                        );
                    } else {
                        customer_s = new StandardCustomer(
                                resultSet.getString("customer_name"),
                                resultSet.getString("customer_address"),
                                resultSet.getString("customer_nif"),
                                resultSet.getString("customer_email")
                        );
                    }
                    Item item = new Item(
                            resultSet.getString("item_code"),
                            resultSet.getString("item_description"),
                            resultSet.getFloat("item_price"),
                            resultSet.getFloat("item_shipping_cost"),
                            resultSet.getInt("item_prep_time")
                    );
                    Order order = new Order((customer_p != null) ? customer_p : customer_s, item, resultSet.getInt("quantity"), resultSet.getString("order_id"), resultSet.getTimestamp("order_date").toLocalDateTime());

                    ordersList.add(order);
                }
            }

        } catch (DuplicateOrderIdException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ordersList;
    }
}