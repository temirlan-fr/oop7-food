package Repository_pack;

public class MenuItemNotAvailable extends RuntimeException {
    public MenuItemNotAvailable(String msg) {
        super(msg);
    }
}