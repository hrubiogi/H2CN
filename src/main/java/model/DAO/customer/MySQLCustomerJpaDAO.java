package model.DAO.customer;

import model.Customer;
import model.Item;
import model.PremiumCustomer;
import model.StandardCustomer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


public class MySQLCustomerJpaDAO {

    private static final String PERSISTENCE_UNIT_NAME = "MyH2CNPersistenceUnit";
    private EntityManagerFactory emFactory;

    public MySQLCustomerJpaDAO() {
        this.emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    public void closeEntityManagerFactory() {
        emFactory.close();
    }

    public void saveCustomer(Customer customer) {
        EntityManager entityManager = emFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(customer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public Customer getCustomerByEmail(String email) {
        EntityManager entityManager = emFactory.createEntityManager();

        try {
            // No es necesario iniciar una transacción para una operación de solo lectura
            Customer customer = entityManager.find(Customer.class, email);

            if (customer == null) {
                System.out.println("El cliente no se encontró en la base de datos");
            }

            return customer;
        } catch (Exception e) {
            // Registra o maneja la excepción según tus necesidades
            System.err.println("Error al obtener el cliente por email: " + e.getMessage());

        } finally {
            entityManager.close();
        }
        return null;
    }

    public List<Customer> getCustomers() {
        EntityManager entityManager = emFactory.createEntityManager();

        try {
            Query query = entityManager.createQuery("SELECT c FROM model.Customer c", Customer.class);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return null;
    }



    public static void main(String[] args){

        MySQLCustomerJpaDAO mySQLCustomerJpaDAO = new MySQLCustomerJpaDAO();

        Customer customer_s = new StandardCustomer("n", "n", "123", "cs@gmail.com");
        Customer customer_p = new PremiumCustomer("b", "b", "456", "cp@gmail.com");
        mySQLCustomerJpaDAO.saveCustomer(customer_s);
        mySQLCustomerJpaDAO.saveCustomer(customer_p);

        //Customer customerS = mySQLCustomerJpaDAO.getCustomerByEmail("cs@gmail.com");
        Customer customerP = mySQLCustomerJpaDAO.getCustomerByEmail("cp@gmail.com");

        //System.out.println(customerS);
        //System.out.println(customerP.customerType());

        List<Customer> allCustomers = mySQLCustomerJpaDAO.getCustomers();
        System.out.println(allCustomers);

        mySQLCustomerJpaDAO.closeEntityManagerFactory();

    }

}


