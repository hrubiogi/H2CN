package model;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import utils.DuplicateOrderIdException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order
{
    @Id
    //@GeneratedValue(generator = "uuid2")
   //@GenericGenerator(name = "uudi2",strategy = "uuid2")
    private String id;
    @ManyToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "itemCode", referencedColumnName = "code")
    private Item item;

    @Column(name = "quantity")
    private int quantity;
    private LocalDateTime date;

    //constructor
    public Order(Customer customer, Item item, int quantity) throws DuplicateOrderIdException {
        /*lanzamiento de excepciones para controlar que los atributos tengan el formato correcto o no puedan
        ser nulos al crear una instancia del objeto Order*/
        /*IllegalArgumentException Si el cliente o el artículo son nulos, o si la cantidad del
         artículo es < 1 */
        if(customer == null){

            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }

        if (item == null){

            throw new IllegalArgumentException("El articulo no puede ser nulo");
        }

        if (quantity < 1){

            throw new IllegalArgumentException("Cantidad del artículo incorrecta para poder hacer un pedido. (menor que 1)");
        }
        this.id = UUID.randomUUID().toString();//se genera de forma automática con el método getUniqueId() y randomUUID (36 caracteres)
        this.customer = customer;
        this.item = item;
        this.quantity = quantity;
        this.date = LocalDateTime.now();
    }

    public Order(Customer customer, Item item, int quantity, String id, LocalDateTime date) throws DuplicateOrderIdException {
        /*lanzamiento de excepciones para controlar que los atributos tengan el formato correcto o no puedan
        ser nulos al crear una instancia del objeto Order*/
        /*IllegalArgumentException Si el cliente o el artículo son nulos, o si la cantidad del
         artículo es < 1 */
        if(customer == null){

            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }

        if (item == null){

            throw new IllegalArgumentException("El articulo no puede ser nulo");
        }

        if (quantity < 1){

            throw new IllegalArgumentException("Cantidad del artículo incorrecta para poder hacer un pedido. (menor que 1)");
        }
        this.id = id;//se genera de forma automática con el método getUniqueId() y randomUUID (36 caracteres)
        this.customer = customer;
        this.item = item;
        this.quantity = quantity;
        this.date = date;
    }

    public Order() {
    }

    public String getId()
    {
        return  id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }

    public Item getItem()
    {
        return item;
    }

    public void setItem(Item item)
    {
        this.item = item;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public LocalDateTime getDate()
    {
        return date;
    }

    public void setDate(LocalDateTime date)
    {
        this.date = date;
    }



    /*
     * Este método compara la fecha del pedido con la fecha de comprobación del pedido.
     * Si la fecha actual es mayor a la fecha de creación del pedido más el tiempo de preparación
     * eso hace que el pedido ya se haya enviado.
     * */
    public boolean orderIsSent()
    {
        LocalDateTime estimatedDeliveryDate = date.plusMinutes(item.getPrepTime());
        LocalDateTime dateNow = LocalDateTime.now();
        boolean encontrado = false;
        if(dateNow.isAfter(estimatedDeliveryDate)){
            return true;

        }else {

            return false;
        }
    }
    public float shippingCost()
    {
        String customerType = customer.customerType();
        float itemShippingCost = item.getShippingCost();

        if (customerType.equalsIgnoreCase("premium")){

            return itemShippingCost * customer.shippingDisc();

        }else{
            return itemShippingCost;
        }
    }
    public String formatDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return date.format(formatter);
    }
    @Override
    public String toString() {
        boolean isSent = orderIsSent();

        return "\n<------------Order------------>" + '\n' +
                "id: " + id + '\n' +
                "customer NIF: " + customer.getNif() + '\n' +
                "customer name: " + customer.getName() + '\n' +
                "customer email: " + customer.getEmail() + '\n' +
                "customer address: " + customer.getAddress() + '\n' +
                "item code: " + item.getCode() + '\n' +
                "item description: " + item.getDescription() + '\n' +
                "item price: " + item.getPrice() + '\n' +
                "quantity: " + quantity + '\n' +
                "shippingCost: " + shippingCost() + '\n' +
                "full price: " + ((item.getPrice() * quantity) + shippingCost()) + '\n' +
                "date: " + formatDate() + '\n' +
                "is sent: " + isSent + '\n' +
                " ----------------------------";
    }
}