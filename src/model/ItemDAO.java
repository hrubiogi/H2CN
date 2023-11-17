package model;

import java.sql.ResultSet;

public interface ItemDAO {
    void saveItem(String code, String description, float price, float shippingCost, int prepTime);
    void listItems();
}
