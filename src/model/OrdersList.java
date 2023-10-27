package model;

import java.util.ArrayList;

public class OrdersList extends List<Order>
{
    //constructor
    public OrdersList()
    {
        super();
    }

    public void getOrders(){
        System.out.println(getList());
    }

    private boolean isOrderIdInUse(String orderId) {
        ArrayList<Order> orders = getList();
        for (Order order : orders) {
            if (order.getId().equals(orderId)) {
                return true; // El ID ya está en uso
            }
        }
        return false; // El ID no está en uso
    }
    public void saveOrder(Customer customer, Item item, int quantity) {

            try {
                Order order = new Order(customer, item, quantity);
                add(order);
                System.out.println(order);
            } catch (IllegalArgumentException e) {
                System.out.print("Tipo de dato no valido: " + e.getMessage());
            } catch (DuplicateOrderIdException e){
                System.out.print("Error: " + e.getMessage());
            }
        }


    public void deleteOrder(String id) {
        getList().removeIf(order -> !order.orderIsSent() && order.getId().equals(id));
    }

    public void getPendingOrdersByCustomer(Customer customer) {
        ArrayList<Order> orders = getList();
        if (customer != null) {
            boolean found = false;  // Variable para controlar si se encontraron pedidos


            for (Order order : orders) {
                if (!order.orderIsSent() && order.getCustomer().equals(customer)) {
                    System.out.println("Pedidos en preparacion para el cliente " + customer.getName() + ": ");
                    System.out.println(order);
                    found = true;  // Se encontró al menos un pedido
                }
            }

            if (!found) {
                System.out.println("No se encontraron pedidos en preparación para el cliente " + customer.getName());
            }
        } else {
            System.out.println("Cliente no existe");
        }
    }

    public void getPendingOrders() {
        ArrayList<Order> orders = getList();
        if (orders.isEmpty()) {
            System.out.println("La lista está vacía. No hay pedidos pendientes.");
            return;
        }
        for (Order order : orders) {
            if (!order.orderIsSent()) {
                System.out.println(order);
            }
        }
    }

    public void getSentOrdersByCustomer(Customer customer) {
        ArrayList<Order> orders = getList();
        if (customer != null) {
            boolean found = false;  // Variable para controlar si se encontraron pedidos


            for (Order order : orders) {
                if (order.orderIsSent() && order.getCustomer().equals(customer)) {
                    System.out.println("Pedidos enviados para el cliente " + customer.getName() + ": ");
                    System.out.println(order);
                    found = true;  // Se encontró al menos un pedido
                }
            }

            if (!found) {
                System.out.println("No se encontraron pedidos enviados para  " + customer.getName() + '\n' + "Nif: " + customer.getNif());
            }
        } else {
            System.out.println("Cliente no existe");
        }
    }


    public void getSentOrders() {
        ArrayList<Order> orders = getList();
        if (orders.isEmpty()) {
            System.out.println("La lista está vacía. No hay pedidos enviados.");

        }
        System.out.println("Pedidos enviados: ");
        for (Order order : orders) {
            if (order.orderIsSent()) {
                System.out.println(getList());
            }
        }
    }


    @Override
    public String toString() {
        return "OrdersList{" +
                "list=" + list +
                "} " + super.toString();
    }
}