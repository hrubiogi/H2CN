package model.DAO.order;

import model.*;
import model.DAO.customer.MySQLCustomerJpaDAO;
import utils.DuplicateOrderIdException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Queue;

public class MySQLOrderJpaDAO implements OrderDAO{

    private static final String PERSISTENCE_UNIT_NAME = "MyH2CNPersistenceUnit";

    private EntityManagerFactory emFactory;

    public MySQLOrderJpaDAO() {
        emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    private void closeEntityManagerFactory() {
        if (emFactory != null && emFactory.isOpen()) {
            emFactory.close();
        }
    }

    @Override
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
            this.closeEntityManagerFactory();
        }
    }

    @Override
    public List<Order> getOrders() {
        EntityManager entityManager = emFactory.createEntityManager();

        try {
            Query query = entityManager.createQuery("SELECT o FROM model.Order o", Order.class);
            List<Order> ordersList = query.getResultList();
            return ordersList;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
                this.closeEntityManagerFactory();
            }
        }
    }

    @Override
    public List<Order> getPendingOrders() {
        EntityManager entityManager = emFactory.createEntityManager();

        try {
            Query query = entityManager.createQuery("SELECT o FROM Order o JOIN o.item i WHERE FUNCTION('TIMESTAMPADD', MINUTE, i.prepTime, o.date) > CURRENT_TIMESTAMP", Order.class);
            return query.getResultList();
        } finally {
                entityManager.close();
                this.closeEntityManagerFactory();
        }
    }

    @Override
    public List<Order> getPendingOrdersByCustomer(Customer customer) {
        EntityManager entityManager = emFactory.createEntityManager();

        try {
            Query query = entityManager.createQuery(
                    "SELECT o FROM Order o JOIN o.item i WHERE FUNCTION('TIMESTAMPADD', MINUTE, i.prepTime, o.date) > CURRENT_TIMESTAMP AND o.customer = :targetCustomer"
                    , Order.class);

            query.setParameter("targetCustomer", customer);

            return query.getResultList();
        } finally {
            entityManager.close();
            this.closeEntityManagerFactory();
        }
    }

    @Override
    public List<Order> getSentOrders() {
        EntityManager entityManager = emFactory.createEntityManager();

        try {
            Query query = entityManager.createQuery("SELECT o FROM Order o JOIN o.item i WHERE FUNCTION('TIMESTAMPADD', MINUTE, i.prepTime, o.date) < CURRENT_TIMESTAMP", Order.class);
            return query.getResultList();
        } finally {
            entityManager.close();
            this.closeEntityManagerFactory();
        }
    }

    @Override
    public List<Order> getSentOrdersByCustomer(Customer customer) {
        EntityManager entityManager = emFactory.createEntityManager();

        try {
            Query query = entityManager.createQuery(
                    "SELECT o FROM Order o JOIN o.item i WHERE FUNCTION('TIMESTAMPADD', MINUTE, i.prepTime, o.date) < CURRENT_TIMESTAMP AND o.customer = :targetCustomer"
                    , Order.class);

            query.setParameter("targetCustomer", customer);

            return query.getResultList();
        } finally {
            entityManager.close();
            this.closeEntityManagerFactory();
        }
    }

    @Override
    public Order getOrderById(String id) {
        EntityManager entityManager = emFactory.createEntityManager();

        try {
            Order order = entityManager.find(Order.class, id);
            return order;
        } finally {
            entityManager.close();
            this.closeEntityManagerFactory();
        }
    }

    @Override
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
            entityManager.close();
            this.closeEntityManagerFactory();
        }
    }
}

