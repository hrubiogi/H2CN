package model;

import java.sql.ResultSet;
import java.util.ArrayList;

public interface ItemDAO {
    void saveItem(String code, String description, float price, float shippingCost, int prepTime);
    ArrayList<Item> listItems();
}
