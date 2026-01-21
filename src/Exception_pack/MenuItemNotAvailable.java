package Exception_pack;

public class MenuItemNotAvailable extends RuntimeException {
    public MenuItemNotAvailable(String msg) {
        super(msg);
    }
}