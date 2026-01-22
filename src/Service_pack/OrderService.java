package Service_pack;

import Entity_pack.MenuItem;
import Exception_pack.InvalidQuantity;
import Repository_pack.OrderItemRep;
import Repository_pack.OrderRep;

public class OrderService {

    private final OrderRep orderRepo = new OrderRep();
    private final OrderItemRep itemRepo = new
            OrderItemRep();
    private final MenuService menuService = new MenuService();

    public int placeOrder(int customerId, int menuItemId, int quantity) {

        if (quantity <= 0) {
            throw new InvalidQuantity("Quantity must be greater than 0");
        }

        MenuItem item =
                menuService.getAvailableMenuItem(menuItemId);

        int orderId = orderRepo.createOrder(customerId);
        itemRepo.addItem(orderId, item.getId(), quantity);

        return orderId;
    }

    public void completeOrder(int orderId) {
        orderRepo.markCompleted(orderId);
    }
}
