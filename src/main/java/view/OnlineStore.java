package view;

import java.sql.Connection;
import java.sql.SQLException;

import utils.ConnectDB;

public class OnlineStore {
    public static void main(String[] args)
    {
        GestionOS gestion = new GestionOS();
        gestion.menu();
        }

    
}