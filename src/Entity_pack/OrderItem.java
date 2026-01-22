package Entity_pack;

public class OrderItem {
    private int orderId;
    private int menuItemId;
    private int quantity;

    public OrderItem(int orderId, int menuItemId, int quantity, int anInt) {
        this.orderId = orderId;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
    }

    public int getOrderId() {
        return orderId;
    }
    public int getMenuItemId() {
        return menuItemId;
    }
    public int getQuantity() {
        return quantity;
    }
}