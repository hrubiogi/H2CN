package view;
import controller.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import model.Customer;
import model.Item;
import model.Order;
import utils.DuplicateOrderIdException;
import utils.ValueIsEmptyException;

public class GestionOS
{

    Scanner keyboard = new Scanner(System.in);

    Controller controller = new Controller();


    /**
     * 
     */
    void menu() {
        boolean leave = false;
        char action;

        do {
            action = askManagement();
            while (action == 'A') {
                action = askManagement();
            }

            switch (action) {
                case '1':
                    action = askAction('1');
                    while (action == 'A') {
                        action = askAction('1');
                    }

                    switch (action) {
                        case '1':
                            addItemHandler();
                            break;
                        case '2':
                            ArrayList<Item> itemList = controller.getData().getItemListDAO();
                            System.out.println("\n<------------Lista de Artículos------------->");
                            System.out.println(itemList);
                            System.out.println("<----------------------------->");
                            break;
                        case '0':
                            break;
                    }

                    break;
                case '2':
                    action = askAction('2');

                    while (action == 'A') {
                        action = askAction('2');
                    }

                    switch (action) {
                        case '1':
                            addCustomerHandler(null);
                            break;
                        case '2':
                            ArrayList<Customer> customerList = controller.getData().getCustomerList();
                            System.out.println("\n<------------Lista de Clientes------------->");
                            System.out.println(customerList);
                            System.out.println("<----------------------------->");
                            break;
                        case '3':
                            ArrayList<Customer> stdCustomerList = controller.getData().getStdCustomerList();
                            System.out.println("\n<------------Lista de Clientes Estándar------------->");
                            System.out.println(stdCustomerList);
                            System.out.println("<----------------------------->");
                            break;
                        case '4':
                            ArrayList<Customer> premiumCustomerList = controller.getData().getPremiumCustomerList();
                            System.out.println("\n<------------Lista de Clientes Premium------------->");
                            System.out.println(premiumCustomerList);
                            System.out.println("<----------------------------->");
                            break;
                        case '0':
                            break;
                    }

                    break;
                case '3':
                    action = askAction('3');

                    while (action == 'A') {
                        action = askAction('3');
                    }

                    switch (action) {
                        case '1':
                            addOrderHandler();
                            break;
                        case '2':
                            ArrayList<Order> pendingOrders = showPendingOrdersHandler();
                            System.out.println("\n<---------Pedidos pendientes--------->");
                            System.out.println(pendingOrders);
                            System.out.println("<-------------------------------------->");
                            break;
                        case '3':
                            ArrayList<Order> sentOrders = showSentOrdersHandler();
                            System.out.println("\n<----------Pedidos enviados---------->");
                            System.out.println(sentOrders);
                            System.out.println("<-------------------------------------->");
                            break;
                        case '4':
                            deleteOrderHandler();
                            break;
                        case '0':
                            break;
                    }

                    break;
                case '0':
                    leave = true;
            }
        } while (!leave);
    }


    char askManagement() {
        String input;
        char validatedInput;

        System.out.println("¿Qué quiere gestionar?");
        System.out.println("1. Artículo");
        System.out.println("2. Cliente");
        System.out.println("3. Pedido");
        System.out.println("0. Salir de la aplicación");

        input = keyboard.nextLine();

        ArrayList<Integer> availableActions = new ArrayList<>(Arrays.asList(0, 1, 2, 3));

        validatedInput = validateInput(input, availableActions);

        return validatedInput;
    }

    char askAction(char selectedManagement) {
        String input;
        char validatedInput;

        if (selectedManagement == '1') {
            System.out.println("¿Qué acción quiere tomar?");
            System.out.println("1. Añadir artículo");
            System.out.println("2. Mostrar artículo");
            System.out.println("0. Volver al menú");

            input = keyboard.nextLine();

            ArrayList<Integer> availableActions = new ArrayList<>(Arrays.asList(0, 1, 2));

            validatedInput = validateInput(input, availableActions);

            return validatedInput;
        } else if (selectedManagement == '2') {
            System.out.println("¿Qué acción quiere tomar?");
            System.out.println("1. Añadir cliente");
            System.out.println("2. Mostrar todos los clientes");
            System.out.println("3. Mostrar clientes estándar");
            System.out.println("4. Mostrar clientes premium");
            System.out.println("0. Volver al menú");

            input = keyboard.nextLine();

            ArrayList<Integer> availableActions = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4));

            validatedInput = validateInput(input, availableActions);

            return validatedInput;

        } else {
            System.out.println("¿Qué acción quiere tomar?");
            System.out.println("1. Añadir pedido");
            System.out.println("2. Mostrar pedidos pendientes");
            System.out.println("3. Mostrar pedidos enviados");
            System.out.println("4. Eliminar pedido");
            System.out.println("0. Volver al menú");

            input = keyboard.nextLine();

            ArrayList<Integer> availableActions = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4));

            validatedInput = validateInput(input, availableActions);

            return validatedInput;
        }
    }

    char validateInput (String input, ArrayList<Integer> availableActions) {


        if (!availableActions.contains(Character.getNumericValue(input.charAt(0)))) {
            System.out.println("Código incorrecto, vuelva a intentarlo");
            return 'A';
        }
        return input.charAt(0);
    }

    String askField(String stringToPrint) {

        System.out.println(stringToPrint);

        return keyboard.nextLine();
    }

    void addItemHandler () {
        try {
            String code = askField("Introduzca código de artículo: ");
            stringIsEmpty(code);
            String description = askField("Introduzca descripción de artículo: ");
            stringIsEmpty(description);
            float price = Float.parseFloat((askField("Introduzca precio de artículo: ")));
            float shippingCost = Float.parseFloat(askField("Introduzca precio de envío de artículo: "));
            int prepTime = Integer.parseInt(askField("Introduzca tiempo de preparación de artículo: "));
            //controller.getData().getItemsList().saveItem(code, description, price, shippingCost, prepTime);
            controller.getData().getDaoFactory().createItemDAO().saveItem(code, description, price, shippingCost, prepTime);

        } catch (ValueIsEmptyException e) {
            System.out.println(e.getMessage());
            addItemHandler();
        } catch (IllegalArgumentException e) {
            System.out.println("Ha introducido un valor incorrecto, vuelva a intentarlo");
            addItemHandler();
        }
    }

    void addCustomerHandler (String emailInput) {
        try {
            String name = askField("Introduzca nombre del cliente: ");
            stringIsEmpty(name);
            String address = askField("Introduzca dirección del cliente: ");
            stringIsEmpty(address);
            String nif = askField("Introduzca nif del cliente: ");
            stringIsEmpty(nif);
            String email;
//            String email = askField("Introduzca email del cliente: ");
//            System.out.println(email);
//            stringIsEmpty(email);
            if(emailInput == null){
                email = askField("Introduzca email del cliente: ");
            }else{
                email = emailInput;
            }

            int isPremium = Integer.parseInt(askField("Presione 2 si se trata de un cliente premium, 1 si es standard: "));

            if (isPremium == 2) {
                controller.getData().saveCustomer(name, address, nif, email, true);

            } else {
                controller.getData().saveCustomer(name, address, nif, email, false);
            }
        } catch (ValueIsEmptyException e) {
            System.out.println(e.getMessage());
            addCustomerHandler(emailInput);
        }
    }
    public void stringIsEmpty(String nameInput) throws ValueIsEmptyException {
        if(nameInput.isEmpty()) {
            throw new ValueIsEmptyException("El campo no puede estar vacío, vuelva a intentarlo");
        }
    }

   void addOrderHandler() {
    try {
        // Pedir información para el pedido
        String email = askField("Introduzca el email del cliente relacionado al pedido: ");
        stringIsEmpty(email);
        Customer customer = controller.getData().getCustomerByEmail(email);

        if (customer == null) {
            System.out.println("No se ha encontrado ningún cliente con este e-mail, desea crearlo?");
            int reuseEmail = Integer.parseInt(askField("Presione 1 si quiere crearlo, 0 si desa volver al menú: "));
            if (reuseEmail == 0) {
                return;
            }
            addCustomerHandler(email);
            customer = controller.getData().getCustomerByEmail(email);
        }

        String itemCode = askField("Introduzca el código del artículo relacionado al pedido: ");
        stringIsEmpty(itemCode);
        Item item = controller.getData().getItemByCode(itemCode);

        while (item == null) {
            int returnVar = Integer.parseInt(askField("Presione 1 si quiere volver a introducir un código, 0 si desa volver al menú: "));
            if (returnVar == 0) {
                return;
            }
            itemCode = askField("Código de artículo incorrecto, vuelva a intentarlo ");
            item = controller.getData().getItemByCode(itemCode);
        }

        int quantity = Integer.parseInt(askField("Introduzca la cantidad de artículos del pedido: "));

        controller.getData().saveOrder(customer, item, quantity);

    } catch (ValueIsEmptyException | NumberFormatException e) {
        System.out.println(e.getMessage());
        addOrderHandler();
    } catch (IllegalArgumentException e) {
        System.out.println("Ha introducido un valor incorrecto, vuelva a intentarlo");
        addOrderHandler();
    } catch (DuplicateOrderIdException e) {
        throw new RuntimeException(e);
    }
   }

    void deleteOrderHandler() {
        String id = askField("Introduzca número de identificación del pedido a eliminar: ");
        controller.getData().deleteOrder(id);
    }

    ArrayList<Order> showPendingOrdersHandler() {
        String input;
        ArrayList<Order> pendingOrders = new ArrayList<>();

        System.out.println("Introduzca el correo del cliente para mostrar sus pedidos pendientes o 0 para mostrarlos todos");
        input = keyboard.nextLine();

        if (Objects.equals(input, "0")) {
            pendingOrders = controller.getData().getPendingOrdersListDAO();
        } else {
            Customer customer = controller.getData().getCustomerByEmail(input);
            pendingOrders = controller.getData().getPendingOrdersByCustomerListDAO(customer);
        }
        return pendingOrders;
    }

    ArrayList<Order> showSentOrdersHandler() {
        String input;
        ArrayList<Order> sentOrders = new ArrayList<>();

        System.out.println("Introduzca el correo del cliente para mostrar sus pedidos enviados o 0 para mostrarlos todos");
        input = keyboard.nextLine();

        if (Objects.equals(input, "0")) {
            sentOrders = controller.getData().getSentOrdersListDAO();
        } else {
            Customer customer = controller.getData().getCustomerByEmail(input);
            sentOrders = controller.getData().getSentOrdersByCustomerListDAO(customer);
        }
        return sentOrders;
    }
}

