package view;
import controller.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import model.Customer;
import model.StandardCustomer;
import model.PremiumCustomer;
import model.Item;
import model.Order;

public class GestionOS
{

    Scanner keyboard = new Scanner(System.in);

    Controller controller = new Controller();


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
                            controller.getData().getItemsList().getItems();
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
                            addCustomerHandler();
                            break;
                        case '2':
                            controller.getData().getCustomersList().getCustomers();
                            break;
                        case '3':
                            controller.getData().getCustomersList().getStdCustomers();
                            break;
                        case '4':
                            controller.getData().getCustomersList().getPremCustomers();
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
                            controller.getData().getOrdersList().getPendingOrders();
                            break;
                        case '3':
                            controller.getData().getOrdersList().getSentOrders();
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
        String input;

        System.out.println(stringToPrint);

        return keyboard.nextLine();
    }

    void addItemHandler () {
        String code = askField("Introduzca código de artículo: ");
        String description = askField("Introduzca descripción de artículo: ");
        float price = Float.parseFloat(askField("Introduzca precio de artículo: "));
        float shippingCost = Float.parseFloat(askField("Introduzca precio de envío de artículo: "));
        int prepTime = Integer.parseInt(askField("Introduzca tiempo de preparación de artículo: "));

        controller.getData().getItemsList().saveItem(code, description, price, shippingCost, prepTime);
    }

    void addCustomerHandler () {
        String name = askField("Introduzca nombre del cliente: ");
        String address = askField("Introduzca dirección del cliente: ");
        String nif = askField("Introduzca nif del cliente: ");
        String email = askField("Introduzca email del cliente: ");
        int isPremium = Integer.parseInt(askField("Presione 1 si se trata de un cliente premium: "));

        if (isPremium != 1) {
            controller.getData().getCustomersList().saveCustomer(name, address, nif, email, false);

        } else {
            controller.getData().getCustomersList().saveCustomer(name, address, nif, email, true);
        }

    }

    void addOrderHandler () {
        String email = askField("Introduzca email del cliente relacionado al pedido: ");
        Customer customer = controller.getData().getCustomerByEmail(email);

        if (customer == null) {
            addCustomerHandler();
            customer = controller.getData().getCustomerByEmail(email);
        }

        String code = askField("Introduzca código del artículo relacionado al pedido: ");
        Item item = controller.getData().getItemsList().getItemByCode(code);

        while(item == null) {
            code = askField("Código de artículo incorrecto, vuelva a intentarlo ");
            item = controller.getData().getItemsList().getItemByCode(code);
        }

        long id = Math.round(Math.floor(Math.random() *(100000 - 0 + 1) + 0));
        int quantity = Integer.parseInt(askField("Introduzca la cantidad de artículos del pedido: "));
        Date date = new Date();

        controller.getData().getOrdersList().saveOrder(id, customer, item, quantity, date);
    }

    void deleteOrderHandler() {
        long id = Long.parseLong(askField("Introduzca número de identificación del pedido a eliminar: "));
        controller.getData().getOrdersList().deleteOrder(id);
    }
}

