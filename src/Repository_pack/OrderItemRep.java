package Repository_pack;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class OrderItemRep {

    public void addItem(int orderId, int menuItemId, int quantity) {
        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO order_items(order_id, menu_item_id, quantity) VALUES (?, ?, ?)");
            ps.setInt(1, orderId);
            ps.setInt(2, menuItemId);
            ps.setInt(3, quantity);
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
