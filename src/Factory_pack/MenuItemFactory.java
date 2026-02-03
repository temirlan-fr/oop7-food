package Factory_pack;

import Entity_pack.MenuItem;

public class MenuItemFactory {

    public static MenuItem createMenuItem(String type, int id, String name, double price, boolean available) {
        switch (type.toLowerCase()) {
            case "burger":
                return new MenuItem.Builder()
                        .setId(id)
                        .setName(name)
                        .setPrice(price)
                        .setAvailable(available)
                        .build();
            case "garnir":
                return new MenuItem.Builder()
                        .setId(id)
                        .setName(name)
                        .setPrice(price)
                        .setAvailable(available)
                        .build();
            case "drink":
                return new MenuItem.Builder()
                        .setId(id)
                        .setName(name + " Drink")
                        .setPrice(price)
                        .setAvailable(available)
                        .build();
            case "dish":
                return new MenuItem.Builder()
                        .setId(id)
                        .setName(name)
                        .setPrice(price)
                        .setAvailable(available)
                        .build();
            default:
                throw new IllegalArgumentException("Unknown menu item type: " + type);
        }
    }
}