package Factory_pack;

import Entity_pack.MenuItem;
import Builder_pack.ComboBuilder;
import java.util.List;

public class MenuItemFactory {

    public static MenuItem createMenuItem(String type, int id, String name, double price, boolean available) {
        switch (type.toLowerCase()) {
            case "burger":
            case "garnir":
            case "drink":
            case "dish":
                return new MenuItem.Builder()
                        .setId(id)
                        .setName(name)
                        .setPrice(price)
                        .setAvailable(available)
                        .build();
            case "combo": // Combo қосу
                MenuItem combo = new MenuItem.Builder()
                        .setId(id)
                        .setName(name + " (Combo)")
                        .setPrice(price)
                        .setAvailable(available)
                        .build();
                return combo;
            default:
                throw new IllegalArgumentException("Unknown menu item type: " + type);
        }
    }

    public static List<MenuItem> createComboItems(MenuItem... items) {
        ComboBuilder combo = new ComboBuilder();
        for(MenuItem item : items) combo.addItem(item);
        return combo.build();
    }
}
