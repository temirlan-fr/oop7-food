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
import Factory_pack.MenuItemFactory;

public class Main {
    public static void main(String[] args) {

        Repository<MenuItem> menuRepo = new MenuItemRep();
        Repository<Order> orderRepo = new OrderRep();
        Repository<OrderItem> itemRepo = new OrderItemRep();

        MenuService menuService = new MenuService(new MenuItemRep());
        OrderService orderService = new OrderService(new OrderRep(), new OrderItemRep(), menuService);
        PaymentService paymentService = new PaymentService();

        MenuItem burger = MenuItemFactory.createMenuItem("Burger", 1, "Chicken_Burger", 1500, true);
        MenuItem garnir = MenuItemFactory.createMenuItem("Garner", 2, "Fries", 800, true);
        MenuItem cola = MenuItemFactory.createMenuItem("Drink", 3, "Coca Cola", 500, true);
        MenuItem dish = MenuItemFactory.createMenuItem("Dish", 4, "Fish", 500, true);

        System.out.println("- Menu -");
        System.out.println("ID: " + burger.getId() + " | Name: " + burger.getName() + " | Price: " + burger.getPrice() + " | Available: " + burger.isAvailable());
        System.out.println("ID: " + garnir.getId() + " | Name: " + garnir.getName() + " | Price: " + garnir.getPrice() + " | Available: " + garnir.isAvailable());
        System.out.println("ID: " + cola.getId() + " | Name: " + cola.getName() + " | Price: " + cola.getPrice() + " | Available: " + cola.isAvailable());
        System.out.println("ID: " + dish.getId() + " | Name: " + dish.getName() + " | Price: " + dish.getPrice() + " | Available: " + dish.isAvailable());

        int customerId = 2;
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

        System.out.println("\n- Menu sorted by price ascending -");
        List<MenuItem> sortedMenu = menuService.getSortedMenuByPrice(true);
        for (MenuItem item : sortedMenu) {
            System.out.println(item.getName() + " | Price: " + item.getPrice());
        }
    }
}
