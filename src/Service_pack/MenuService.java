package Service_pack;

import Entity_pack.MenuItem;
import Exception_pack.MenuItemNotAvailable;
import Repository_pack.MenuItemRep;

public class MenuService {
    private final MenuItemRep menuRepo = new
            MenuItemRep();

    public MenuItem getAvailableMenuItem(int id) {
        MenuItem item = menuRepo.findById(id);
        System.out.println("Fetched MenuItem: " + item);
        if (item == null || !item.isAvailable()) {
            throw new MenuItemNotAvailable("Menu item not available");
        }
        return item;
    }
}