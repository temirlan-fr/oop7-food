package Service_pack;

import Entity_pack.MenuItem;
import Entity_pack.OrderItem;
import Exception_pack.InvalidQuantity;
import Exception_pack.OrderNotFound;
import Repository_pack.OrderItemRep;
import Repository_pack.OrderRep;
import Service_pack.MenuService;

import java.util.List;

public class OrderService {

    private final OrderRep orderRepo;
    private final OrderItemRep itemRepo;
    private final MenuService menuService;

    public OrderService(OrderRep orderRepo, OrderItemRep itemRepo, MenuService menuService) {
        this.orderRepo = orderRepo;
        this.itemRepo = itemRepo;
        this.menuService = menuService;
    }

    public int placeOrder(int customerId, int menuItemId, int quantity) {
        if (quantity <= 0) throw new InvalidQuantity("Quantity must be > 0");

        MenuItem item = menuService.getAvailableMenuItem(menuItemId);

        int orderId = orderRepo.createOrder(customerId);  // енді қатесіз
        if (orderId == -1) throw new OrderNotFound("Order creation failed");

        itemRepo.addItem(orderId, menuItemId, quantity); // енді қатесіз
        return orderId;
    }

    public void completeOrder(int orderId) {
        boolean updated = orderRepo.markCompleted(orderId);
        if (!updated) throw new OrderNotFound("Order not found!");
    }

    public double calculateTotalAmount(int orderId){
        List<OrderItem> items = itemRepo.findByOrderId(orderId);
        double total = 0;
        for (OrderItem item : items) {
            MenuItem menuItem = menuService.getAvailableMenuItem(item.getMenuItemId());
            total += menuItem.getPrice() * item.getQuantity();
        }
        return total;
    }

    public List<OrderItem> getOrderItems(int orderId) {
        return itemRepo.findByOrderId(orderId);
    }


    public int placeComboOrder(int customerId, List<MenuItem> comboItems) {

        int orderId = orderRepo.createOrder(customerId);
        if (orderId == -1) throw new OrderNotFound("Order creation failed");

        for (MenuItem item : comboItems) {
            MenuItem availableItem = menuService.getAvailableMenuItem(item.getId());
            itemRepo.addItem(orderId, availableItem.getId(), 1);
        }
        return orderId;
    }

}
