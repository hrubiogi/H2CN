
import java.util.Scanner;

public class GestionOS
{
    private Controller controller;
    Scanner teclado = new Scanner(System.in);
    public GestionOS()
    {
        controller = new Controller();
    }
    public void menu()
    {
        boolean salir = false;
        char opcio;
        do
        {
            System.out.println("1. Gestión Artículos");
            System.out.println("2. Gestión Clientes");
            System.out.println("3. Gestión Pedidos");
            System.out.println("0. Salir");
            opcio = pedirOpcion();
            switch (opcio)
            {
                case '1':
                    itemHandler();
                    break;
                case '2':
                    customerHandler();
                    break;
                case '3':
                    orderHandler();
                    break;
                case '0':
                    salir = true;
            }
        } while (!salir);
    }
    char pedirOpcion()
    {
        String resp;
        System.out.println("Elige una opción (1,2,3 o 0): ");
                resp = teclado.nextLine();
        if (resp.isEmpty())
        {
            resp = " ";
        }
        return resp.charAt(0);
    }

    void itemHandler()
    {
        boolean salir = false;
        char opcio;
        do
        {
            System.out.println("1. Añadir artículo?");
            System.out.println("2. Lista de artículos?");
            System.out.println("0. Salir");
            opcio = pedirOpcion();
            switch (opcio)
            {
                case '1':
                    System.out.println("Introduce el código: ");
                    String code = teclado.nextLine();
                    System.out.println("Introduce la descripción: ");
                    String description = teclado.nextLine();
                    System.out.print("Introduce el precio: ");
                    float price = Float.parseFloat(teclado.nextLine());
                    System.out.print("Introduce el precio de envío: ");
                    float shippingCost = Float.parseFloat(teclado.nextLine());
                    System.out.print("Introduce el tiempo de preparación: ");
                    int prepTime = Integer.parseInt(teclado.nextLine());
                    controller.getData().saveItem(code, description, price, shippingCost, prepTime);
                    break;
                case '2':
                    controller.getData().getItems();
                    break;
                case '0':
                    salir = true;
            }
        } while (!salir);
        menu();
    }

    void customerHandler()
    {
        // TO-BE-DONE
    }

    void orderHandler()
    {
        // TO-BE-DONE
    }
}

