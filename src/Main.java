import Entity_pack.MenuItem;
import Entity_pack.OrderItem;
import Service_pack.MenuService;
import Service_pack.OrderService;
import Service_pack.PaymentService;
import Repository_pack.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        PaymentService paymentService = new PaymentService();
        MenuService menuService = new MenuService();

        int customerId = 2;
        int orderId = orderService.placeOrder(customerId, 1, 2);

        System.out.println("Created orderId = " + orderId);

        System.out.println("\n- Order Receipt -");
        try (Connection con = DatabaseConnection.getConnection()) {

            PreparedStatement psCustomer = con.prepareStatement("SELECT name FROM customers WHERE id=?");
            psCustomer.setInt(1, customerId);
            ResultSet rsCustomer = psCustomer.executeQuery();
            if (rsCustomer.next()) {
                System.out.println("Customer: " + rsCustomer.getString("name"));
            }

            List<OrderItem> items = orderService.getOrderItems(orderId);
            double total = 0;
            for (int i = 0; i < items.size(); i++) {
                OrderItem item = items.get(i);
                MenuItem menuItem = menuService.getAvailableMenuItem(item.getMenuItemId());
                double itemTotal = menuItem.getPrice() * item.getQuantity();
                total += itemTotal;
                System.out.println("Ordered: " + menuItem.getName() + " | Quantity: " + item.getQuantity() + " | Price: " + menuItem.getPrice() + " | Subtotal: " + itemTotal);
            }

            System.out.println("\n");
            System.out.println("Total amount: " + total);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        paymentService.pay(orderService.calculateTotalAmount(orderId));
        orderService.completeOrder(orderId);
        System.out.println("Order completed successfully");
    }
}
