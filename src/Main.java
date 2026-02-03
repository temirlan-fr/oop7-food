import Entity_pack.MenuItem;
import Entity_pack.Order;
import Entity_pack.OrderItem;
import Repository_pack.*;
import Service_pack.MenuService;
import Service_pack.OrderService;
import Service_pack.PaymentService;

import Builder_pack.ComboBuilder;
import Factory_pack.MenuItemFactory;
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
        MenuItem garnir = MenuItemFactory.createMenuItem("Garnir", 2, "Fries", 800, true);
        MenuItem cola = MenuItemFactory.createMenuItem("Drink", 4, "Cola", 500, true);
        MenuItem dish = MenuItemFactory.createMenuItem("Dish", 6, "Fish", 500, true);

        System.out.println("- Menu -");
        System.out.println("ID: " + burger.getId() + " | Name: " + burger.getName() + " | Price: " + burger.getPrice() + " | Available: " + burger.isAvailable());
        System.out.println("ID: " + garnir.getId() + " | Name: " + garnir.getName() + " | Price: " + garnir.getPrice() + " | Available: " + garnir.isAvailable());
        System.out.println("ID: " + cola.getId() + " | Name: " + cola.getName() + " | Price: " + cola.getPrice() + " | Available: " + cola.isAvailable());
        System.out.println("ID: " + dish.getId() + " | Name: " + dish.getName() + " | Price: " + dish.getPrice() + " | Available: " + dish.isAvailable() + "\n");

        ComboBuilder combo = new ComboBuilder();
        combo.addItem(burger).addItem(garnir).addItem(cola);

        System.out.println("- Combo Order -");
        combo.build().forEach(item -> System.out.println(item.getName() + " | Price: " + item.getPrice()));
        System.out.println("Total Combo Price: " + combo.getTotalPrice() + "\n");


        int customerId1 = 1; // Temirlan
        int orderId1 = orderService.placeOrder(customerId1, 1, 2);

        System.out.println("\n- Order Receipt for Temirlan -");
        printReceipt(orderId1, customerId1, menuService, orderService);
        paymentService.pay(orderService.calculateTotalAmount(orderId1));
        orderService.completeOrder(orderId1);

        int customerId2 = 2;
        int comboOrderId = orderService.placeComboOrder(customerId2, combo.build());

        System.out.println("\n- Combo Order Receipt -");
        printReceipt(comboOrderId, customerId2, menuService, orderService);
        paymentService.pay(orderService.calculateTotalAmount(comboOrderId));
        orderService.completeOrder(comboOrderId);

    }

    public static void printReceipt(int orderId, int customerId, MenuService menuService, OrderService orderService) {
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
            System.out.println("\nTotal amount: " + total + "\n");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}