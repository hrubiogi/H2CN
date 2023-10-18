import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Order
{
    private int id;
    private Customer customer;
    private Item item;
    private int quantity;
    private LocalDate date;


    public int getId()
    {
        return id;
    }

    public void setId(int id)
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

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public Order(int id, Customer customer, Item item, int quantity, LocalDate date)
    {
        this.id = id;
        this.customer = customer;
        this.item = item;
        this.quantity = quantity;
        this.date = date;
    }


    public boolean orderIsSent()
    {
        return false;
        //TBD
    }

    public float shippingCost()
    {
        return -1;
        //TBD
    }


    @Override
    public String toString()
    {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer +
                ", item=" + item +
                ", quantity=" + quantity +
                ", date=" + date +
                '}';
    }
}
