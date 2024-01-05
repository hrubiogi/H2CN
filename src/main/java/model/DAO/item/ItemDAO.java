package model.DAO.item;

import model.Item;

import java.sql.ResultSet;
import java.util.List;

public interface ItemDAO {
    void saveItem(Item item);
    List<Item> listItems();
    Item getItem(String code);
}
