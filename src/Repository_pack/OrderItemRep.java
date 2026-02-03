package Repository_pack;

import Entity_pack.OrderItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemRep implements Repository<OrderItem> {

    public void addItem(int orderId, int menuItemId, int quantity) {
        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO order_items(order_id, menu_item_id, quantity) VALUES (?, ?, ?)");
            ps.setInt(1, orderId);
            ps.setInt(2, menuItemId);
            ps.setInt(3, quantity);
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<OrderItem> findByOrderId(int orderId) {
        List<OrderItem> list = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM order_items WHERE order_id=?"
            );
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new OrderItem(
                        rs.getInt("order_id"),
                        rs.getInt("menu_item_id"),
                        rs.getInt("quantity")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public OrderItem findById(int id) {
        return null;
    }

    @Override
    public List<OrderItem> findAll() {
        return List.of();
    }

    @Override
    public void save(OrderItem entity) {

    }

    @Override
    public void update(OrderItem entity) {

    }

    @Override
    public void delete(int id) {

    }
}
