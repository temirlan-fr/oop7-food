package Repository_pack;

import Exception_pack.OrderNotFound;
import java.sql.*;

public class OrderRep {

    public int createOrder(int customerId) {
        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO orders(customer_id, status) VALUES (?, 'ACTIVE')",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setInt(1, customerId);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
            throw new RuntimeException("Order not created");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void markCompleted(int orderId) {
        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE orders SET status='COMPLETED' WHERE id=?");
            ps.setInt(1, orderId);

            int updated = ps.executeUpdate();
            if (updated == 0) {
                throw new OrderNotFound("Order not found");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
