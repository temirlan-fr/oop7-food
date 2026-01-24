package Service_pack;

import Entity_pack.MenuItem;
import Entity_pack.OrderItem;
import Exception_pack.InvalidQuantity;
import Exception_pack.OrderNotFound;
import Repository_pack.OrderItemRep;
import Repository_pack.OrderRep;

import java.util.List;

public class OrderService {

    private final OrderRep orderRepo = new OrderRep();
    private final OrderItemRep itemRepo = new OrderItemRep();
    private final MenuService menuService = new MenuService();

    public int placeOrder(int customerId, int menuItemId, int quantity) {

        if (quantity <= 0){ throw new InvalidQuantity("Quantity must be > 0");
        }

        MenuItem item = menuService.getAvailableMenuItem(menuItemId);

        int orderId = orderRepo.createOrder(customerId);
        if (orderId==-1)throw new OrderNotFound("Order creation failed");

        itemRepo.addItem(orderId, menuItemId, quantity);
        return orderId;
    }

    public void completeOrder(int orderId) {
        boolean updated=orderRepo.markCompleted(orderId);
        if(!updated)throw new OrderNotFound("Order not found!");

    }
    public double calculateTotalAmount(int orderId){
    List<OrderItem>items=itemRepo.findByOrderId(orderId);
    double total=0;
    for(OrderItem item:items) {
        MenuItem menuItem =menuService.getAvailableMenuItem(item.getMenuItemId());
        total += menuItem.getPrice()*item.getQuantity();
    }
    return total;
    }
}
