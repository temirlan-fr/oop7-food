import Service_pack.OrderService;
import Service_pack.PaymentService;

public class Main {
    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        PaymentService paymentService = new PaymentService();

        int orderId = orderService.placeOrder(1, 1, 2);
        System.out.println("Created orderId = " + orderId);

        double totalAmount = orderService.calculateTotalAmount(orderId);
        paymentService.pay(totalAmount);

        orderService.completeOrder(orderId);
        System.out.println("Order completed successfully");
    }
}
