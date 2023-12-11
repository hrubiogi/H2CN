package model.DAO.order;

import model.*;
import model.DAO.customer.MySQLCustomerJpaDAO;
import utils.DuplicateOrderIdException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Queue;

public class MySQLOrderJpaDAO {

    private static final String PERSISTENCE_UNIT_NAME = "MyH2CNPersistenceUnit";

    private EntityManagerFactory emFactory;

    public MySQLOrderJpaDAO() {
        emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    public void closeEntityManagerFactory() {
        if (emFactory != null && emFactory.isOpen()) {
            emFactory.close();
        }
    }

    public void saveOrder(Order order) {
        EntityManager entityManager = emFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(order);
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

    public List<Order> getOrders() {
        EntityManager entityManager = emFactory.createEntityManager();

        try {
            Query query = entityManager.createQuery("SELECT o FROM model.Order o", Order.class);
            List<Order> ordersList = query.getResultList();
            return ordersList;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    public List<Order> getPendingOrders() {
        EntityManager entityManager = emFactory.createEntityManager();

        try {

            Query query = entityManager.createQuery("SELECT o FROM Order o JOIN Item i ON o.itemCode = i.code WHERE (o.date + i.prepTime) < :now", Order.class);
            query.setParameter("now", LocalDateTime.now());

            return query.getResultList();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }


    public List<Order> getPendingOrdersByCustomerEmail(Customer customer) {
        EntityManager entityManager = emFactory.createEntityManager();

        try {

            // añadir tiempo en Java
            Query query = entityManager.createQuery(
                    "SELECT o FROM Order o JOIN o.customer c WHERE c.email = :customerEmail AND o.date < :now"
                    , Order.class);

            query.setParameter("customerEmail", customer.getEmail());
            query.setParameter("now", LocalDateTime.now());

            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    public List<Order> getSentOrders() {
        EntityManager entityManager = emFactory.createEntityManager();

        try {
            LocalDateTime now = LocalDateTime.now();

            Query query = entityManager.createQuery("SELECT o FROM Orders JOIN o.itemCode i WHERE :now < FUNCTION('DATE_ADD', 'MINUTE', o.date, i.preptime",
                    Order.class);

            query.setParameter("now", now);
            return query.getResultList();

        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }

        }
    }

    public List<Order> getSentOrdersByCustomer(Customer customer) {
        EntityManager entityManager = emFactory.createEntityManager();

        try {
            LocalDateTime now = LocalDateTime.now();
            Query query = entityManager.createQuery("SELECT o FROM Order o JOIN o.itemCode i WHERE o.email :customerEmail AND :now < FUNCTION('DATE_ADD', 'MINUTE', o.date, i.prepTime)"
                    , Order.class);

            query.setParameter("customerEmail", customer.getEmail());
            query.setParameter("now", now);

            return query.getResultList();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    public void deleteOrder(String orderId) {
        EntityManager entityManager = emFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            Order order = entityManager.find(Order.class, orderId);

            if (order != null) {
                entityManager.remove(order);
                transaction.commit();
                System.out.println("El pedido con el Id " + orderId + " ha sido eliminado exitosamente.");
            } else {
                System.out.println("El pedido con el Id " + orderId + " no se ha encontrado");
            }
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    public static void main(String[] args) throws DuplicateOrderIdException {

        MySQLOrderJpaDAO mySQLOrderJpaDAO = new MySQLOrderJpaDAO();
        Customer customer_s = new StandardCustomer("n", "n", "123", "cs@gmail.com");
        Item newItem = new Item("A4", "Example Item", 10.0f, 2.0f, 1);
        Order order1 = new Order(customer_s, newItem, 3);

        //mySQLOrderJpaDAO.saveOrder(order1);

        //List<Order> orders = mySQLOrderJpaDAO.getOrders();
        List<Order> pendingOrders = mySQLOrderJpaDAO.getPendingOrders();
        //List<Order> pendingOrdersByCustomer = mySQLOrderJpaDAO.getPendingOrdersByCustomerEmail(customer_s);
        System.out.println(pendingOrders);

       // System.out.println(orders);

        mySQLOrderJpaDAO.closeEntityManagerFactory();

    }

}

