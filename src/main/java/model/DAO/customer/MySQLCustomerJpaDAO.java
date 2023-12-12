package model.DAO.customer;

import model.Customer;
import model.Item;
import model.PremiumCustomer;
import model.StandardCustomer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


public class MySQLCustomerJpaDAO implements CustomerDAO{

    private static final String PERSISTENCE_UNIT_NAME = "MyH2CNPersistenceUnit";
    private EntityManagerFactory emFactory;

    public MySQLCustomerJpaDAO() {
        this.emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    private void closeEntityManagerFactory() {
        if (emFactory != null && emFactory.isOpen()) {
            emFactory.close();
        }
    }

    @Override
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
            this.closeEntityManagerFactory();
        }
    }

    @Override
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
            this.closeEntityManagerFactory();
        }
        return null;
    }

    @Override
    public List<Customer> listCustomers() {
        EntityManager entityManager = emFactory.createEntityManager();

        try {
            Query query = entityManager.createQuery("SELECT c FROM Customer c", Customer.class);
            return query.getResultList();
        } catch (Exception e) {
            // Registra o maneja la excepción según tus necesidades
            System.err.println("Error al obtener la lista de clientes: " + e.getMessage());
        } finally {
            entityManager.close();
            this.closeEntityManagerFactory();
        }
        return null;
    }

    @Override
    public List<Customer> listPremiumCustomers() {
        EntityManager entityManager = emFactory.createEntityManager();

        try {
            Query query = entityManager.createQuery("SELECT c FROM Customer c WHERE type = 2", Customer.class);
            return query.getResultList();
        } catch (Exception e) {
            // Registra o maneja la excepción según tus necesidades
            System.err.println("Error al obtener la lista de clientes premium: " + e.getMessage());
        } finally {
            entityManager.close();
            this.closeEntityManagerFactory();
        }
        return null;
    }

    @Override
    public List<Customer> listStdCustomers() {
        EntityManager entityManager = emFactory.createEntityManager();

        try {
            Query query = entityManager.createQuery("SELECT c FROM Customer c WHERE type = 1", Customer.class);
            return query.getResultList();
        }  catch (Exception e) {
            // Registra o maneja la excepción según tus necesidades
            System.err.println("Error al obtener la lista de clientes estándar: " + e.getMessage());

        } finally {
                entityManager.close();
                this.closeEntityManagerFactory();
        }
        return null;
    }
}


