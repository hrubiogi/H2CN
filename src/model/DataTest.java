package model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.GestionOS.*;
import model.ItemsList.*;
import model.OrdersList.*;
import model.CustomersList.*;
import java.lang.Thread.*;


import static org.junit.jupiter.api.Assertions.*;

class DataTest {
    ItemsList itemsList = new ItemsList();
    OrdersList ordersList = new OrdersList();
    @Test
    void itemListTest() {

        itemsList.saveItem("1000", "silla", 10, 3, 120);
        assertEquals("1000", itemsList.getItemByCode("1000").getCode());
    }

    @Test
    void orderCreationTest() {
        Customer c1 = new PremiumCustomer("n", "o", "1", "ejemplo1");
        Item i1 = new Item("1000", "mueble", 1500.99F, 5, 0);/*
        *Dados los valores necesarios para el test, se ha decidido que se simplifiquen los valores de preptime y
        el Thread.sleep a lo mínimo necesario para completar el test.
        */

        ordersList.saveOrder(c1, i1,3);
        System.out.println(ordersList);

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertTrue(ordersList.getList().get(0).orderIsSent());

    }

    @Test
    void getOrders() {



    }
}