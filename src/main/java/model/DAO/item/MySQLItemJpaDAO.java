package model.DAO.item;

import model.Item;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLItemJpaDAO {

    private static final String PERSISTENCE_UNIT_NAME = "MyH2CNPersistenceUnit";

    private EntityManagerFactory emFactory;

    public MySQLItemJpaDAO() {
        emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    public void closeEntityManagerFactory() {
        if (emFactory != null && emFactory.isOpen()) {
            emFactory.close();
        }
    }


    public void saveItem(Item item) {
        EntityManager entityManager = emFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(item);
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

    public void deleteItemById(String itemId) {
        EntityManager entityManager = emFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            // Find the item by ID
            Item item = entityManager.find(Item.class, itemId);

            if (item != null) {
                // If the item exists, remove it
                entityManager.remove(item);
                entityManager.getTransaction().commit();
            } else {
                // Handle the case where the item with the given ID is not found
                System.out.println("Item with ID " + itemId + " not found.");
            }
        } finally {
            entityManager.close();
        }
    }

    public Item getItem(String code) {
        EntityManager entityManager = emFactory.createEntityManager();

        try {
            return entityManager.find(Item.class, code);
        } finally {
            entityManager.close();
        }
    }


    public List<Item> listItems() {
        EntityManager entityManager = emFactory.createEntityManager();

        try {
            Query query = entityManager.createQuery("SELECT i FROM model.Item i", Item.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    // Additional methods for more advanced queries or operations can be added here

    public static void main(String[] args) {
        MySQLItemJpaDAO itemJpaDao = new MySQLItemJpaDAO();

        // Create an item
        Item newItem = new Item("A4", "Example Item", 10.0f, 2.0f, 30);

        // Save the item to the database
        //itemJpaDao.saveItem(newItem);

        // Delete
        //itemJpaDao.deleteItemById("A4");

        // Retrieve the item from the database
        Item retrievedItem = itemJpaDao.getItem("A1");

        // Print the retrieved item
        System.out.println(retrievedItem);

        // Retrieve all items from the database
        List<Item> allItems = itemJpaDao.listItems();
        for (Item item : allItems) {
            System.out.println(item);
        }

        // Close the EntityManagerFactory
        itemJpaDao.closeEntityManagerFactory();
    }

}
