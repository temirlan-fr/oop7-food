import Entity_pack.MenuItem;
import Entity_pack.OrderItem;
import Repository_pack.*;
import Service_pack.MenuService;
import Service_pack.OrderService;
import Service_pack.PaymentService;
import Factory_pack.MenuItemFactory;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        Repository<MenuItem> menuRepo = new MenuItemRep();
        MenuService menuService = new MenuService(menuRepo);
        OrderService orderService = new OrderService(new OrderRep(), new OrderItemRep(), menuService);
        PaymentService paymentService = new PaymentService();

        MenuItem burger = MenuItemFactory.createMenuItem("burger", 1, "Chicken_Burger", 1500, true);
        MenuItem garnir = MenuItemFactory.createMenuItem("garnir", 2, "Fries", 800, true);
        MenuItem cola = MenuItemFactory.createMenuItem("drink", 4, "Cola", 500, true);
        MenuItem dish = MenuItemFactory.createMenuItem("dish", 6, "Fish", 2500, true);

        List<MenuItem> comboMenu = MenuItemFactory.createComboItems(burger, garnir, cola);

        List<MenuItem> sortedMenu = menuService.getSortedMenuByPrice(true);
        System.out.println("--- Menu ---");
        for(MenuItem item : sortedMenu){
            System.out.println(item.getId() + " | " + item.getName() + " | " + item.getPrice());
        }


        int comboId = 100;
        double comboPrice = comboMenu.stream().mapToDouble(MenuItem::getPrice).sum();
        System.out.println("\n--- Combo Menu ---");
        System.out.println(comboId + " | Burger + Fries + Cola Combo | " + comboPrice);

        Scanner scanner = new Scanner(System.in);


        Map<Integer, String[]> customers = new LinkedHashMap<>();
        customers.put(1, new String[]{"Temirlan","87473436246"});
        customers.put(2, new String[]{"Xeniya","87271248719"});
        customers.put(3, new String[]{"Nurbolsyn","87773287427"});
        customers.put(4, new String[]{"Alpamys","87073646241"});
        customers.put(5, new String[]{"Shyngys","87005556677"});


        System.out.println("\nChoose customer ID:");
        for(Map.Entry<Integer, String[]> entry : customers.entrySet()){
            System.out.println(entry.getKey() + " | " + entry.getValue()[0] + " | " + entry.getValue()[1]);
        }

        int customerId = scanner.nextInt();
        String customerName = customers.get(customerId)[0];
        String customerPhone = customers.get(customerId)[1];


        System.out.println("\nOrder combo or individual? (c/i)");
        String choice = scanner.next();

        int orderId = -1;

        if(choice.equalsIgnoreCase("c")) {
            orderId = orderService.placeComboOrder(customerId, comboMenu);
        } else {
            System.out.println("Number of items to order:");
            int n = scanner.nextInt();

            orderId = orderService.createEmptyOrder(customerId);
            for(int i=0;i<n;i++){
                System.out.println("Enter MenuItem ID:");
                int id = scanner.nextInt();
                System.out.println("Enter quantity:");
                int qty = scanner.nextInt();
                orderService.placeOrder(customerId, id, qty, orderId);
            }
        }


        printReceipt(orderId, customerId, customerName, customerPhone, menuService, orderService);
        double total = orderService.calculateTotalAmount(orderId);
        paymentService.pay(total);
        orderService.completeOrder(orderId);
    }

    public static void printReceipt(int orderId, int customerId, String name, String phone, MenuService menuService, OrderService orderService){
        System.out.println("\nCustomer: " + name + " | Phone: " + phone);
        List<OrderItem> items = orderService.getOrderItems(orderId);
        double total = 0;
        for(OrderItem item : items){
            MenuItem menuItem = menuService.getAvailableMenuItem(item.getMenuItemId());
            double sub = menuItem.getPrice() * item.getQuantity();
            total += sub;
            System.out.println("Ordered: " + menuItem.getName() + " | Quantity: " + item.getQuantity() + " | Price: " + menuItem.getPrice() + " | Subtotal: " + sub);
        }
        System.out.println("Total: " + total + "\n");
    }
}
