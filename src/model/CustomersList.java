package model;

import java.util.ArrayList;

public class CustomersList extends List<Customer>
{
    //constructor
    public CustomersList()
    {
        super();

    }

    public void saveCustomer(String name, String address, String nif, String email, boolean isPremium)
    {

        if (isPremium) {
            PremiumCustomer c = new PremiumCustomer(name, address, nif, email);
            this.add(c);
        } else {
            StandardCustomer c = new StandardCustomer(name, address, nif, email);
            this.add(c);
        }

    }

    public void getCustomers()
    {
        System.out.println("Listado de Clientes: ");
        for (int i = 0; i < this.list.size(); i++) {
            System.out.println("------------------------------------");
            System.out.println("Nombre: " + this.list.get(i).getName());
            System.out.println("Dirección: " + this.list.get(i).getAddress());
            System.out.println("Nif: " + this.list.get(i).getNif());
            System.out.println("Email: " + this.list.get(i).getEmail());
            //System.out.println("------------------------------------");
        }

    }

    public void getStdCustomers() {
        //ArrayList<StandardCustomer> standardCustomerList = new ArrayList<>();//revisar tamaño array
        System.out.println("Listado de Clientes Estándar: ");
        for (int i = 0; i < this.list.size(); i++) {
            if (this.list.get(i).customerType() == "S"){
                System.out.println("------------------------------------");
                System.out.println("Nombre: " + this.list.get(i).getName());
                System.out.println("Dirección: " + this.list.get(i).getAddress());
                System.out.println("Nif: " + this.list.get(i).getNif());
                System.out.println("Email: " + this.list.get(i).getEmail());
            }
        }

    }

    public void getPremCustomers() {

        System.out.println("Listado de Clientes Premium: ");
        for (int i = 0; i < this.list.size(); i++) {
            if (this.list.get(i).customerType() == "P"){

                System.out.println("------------------------------------");
                System.out.println("Nombre: " + this.list.get(i).getName());
                System.out.println("Dirección: " + this.list.get(i).getAddress());
                System.out.println("Nif: " + this.list.get(i).getNif());
                System.out.println("Email: " + this.list.get(i).getEmail());
            }
        }

    }

    public Customer getCustomerByEmail(String email){
        for(Customer customer: getList()){
            if(customer.getEmail().equalsIgnoreCase(email)){
                return customer;
            }
        }
        return null;
    }
}
