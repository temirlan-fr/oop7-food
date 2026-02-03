import Entity_pack.MenuItem;
import Entity_pack.Order;
import Entity_pack.OrderItem;
import Repository_pack.*;
import Service_pack.MenuService;
import Service_pack.OrderService;
import Service_pack.PaymentService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Repository<MenuItem> menuRepo = new MenuItemRep();
        Repository<Order> orderRepo = new OrderRep();
        Repository<OrderItem> itemRepo = new OrderItemRep();

        MenuService menuService = new MenuService(new MenuItemRep());
        OrderService orderService = new OrderService(new OrderRep(), new OrderItemRep(), menuService);
        PaymentService paymentService = new PaymentService();

        int customerId = 2; // мысалы Ксения
        int orderId = orderService.placeOrder(customerId, 1, 2);
        System.out.println("Created orderId = " + orderId);

        System.out.println("\n- Order Receipt -");
        try (Connection con = DatabaseConnection.getConnection()) {

            PreparedStatement psCustomer = con.prepareStatement(
                    "SELECT name, phone_number FROM customers WHERE id=?"
            );
            psCustomer.setInt(1, customerId);
            ResultSet rsCustomer = psCustomer.executeQuery();
            if (rsCustomer.next()) {
                System.out.println("Customer: " + rsCustomer.getString("name") + " | Phone: " + rsCustomer.getString("phone_number"));
            }

            List<OrderItem> items = orderService.getOrderItems(orderId);
            double total = 0;
            for (OrderItem item : items) {
                MenuItem menuItem = menuService.getAvailableMenuItem(item.getMenuItemId());
                double itemTotal = menuItem.getPrice() * item.getQuantity();
                total += itemTotal;
                System.out.println("Ordered: " + menuItem.getName() + " | Quantity: " + item.getQuantity() + " | Price: " + menuItem.getPrice() + " | Subtotal: " + itemTotal);
            }

            System.out.println("\nTotal amount: " + total);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        double totalAmount = orderService.calculateTotalAmount(orderId);
        paymentService.pay(totalAmount);

        orderService.completeOrder(orderId);
        System.out.println("Order completed successfully");
    }
}
