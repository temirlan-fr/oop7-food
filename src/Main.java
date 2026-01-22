import Service_pack.OrderService;

public class Main {
    public static void main(String[] args) {

        OrderService orderService = new OrderService();

        // Заказ беру
        int orderId = orderService.placeOrder(1, 1, 2);
        System.out.println("Order created with ID: " + orderId);

        // Заказ толықтау
        orderService.completeOrder(orderId);
        System.out.println("Order completed");
    }
}
