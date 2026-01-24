package Service_pack;

import Entity_pack.MenuItem;
import Exception_pack.MenuItemNotAvailable;
import Repository_pack.MenuItemRep;

public class MenuService {
    private final MenuItemRep menuRepo = new
            MenuItemRep();

    public MenuItem getAvailableMenuItem(int id) {
        MenuItem item = menuRepo.findById(id);
        if (item == null || !item.isAvailable()) {
            throw new MenuItemNotAvailable("Menu item not available");
        }
        return item;
    }
}
