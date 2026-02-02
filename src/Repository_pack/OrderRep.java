package Repository_pack;

import Entity_pack.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderRep implements Repository<Order> {

    private final Connection connection;

    public OrderRep() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to DB", e);
        }
    }

    public int createOrder(int customerId) {
        String sql = "INSERT INTO orders (customer_id, status) VALUES (?, ?) RETURNING id";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, customerId);
            pst.setString(2, "ACTIVE");

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean markCompleted(int orderId) {
        String sql = "UPDATE orders SET status='COMPLETED' WHERE id=?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, orderId);
            int rows = pst.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Order findById(int id) {
        return null;
    }

    @Override
    public List<Order> findAll() {
        return List.of();
    }

    @Override
    public void save(Order entity) {

    }
}
