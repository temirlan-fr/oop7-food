package Repository_pack;

import Entity_pack.Order;
import java.sql.*;

public class OrderRep implements Repository<Order> {
    private final Connection connection;

    public OrderRep() {
        try { connection = DatabaseConnection.getConnection(); }
        catch (SQLException e) { throw new RuntimeException(e); }
    }

    public int createOrder(int customerId) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO orders(customer_id, status) VALUES(?, 'ACTIVE') RETURNING id");
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (SQLException e) { e.printStackTrace(); }
        return -1;
    }

    public boolean markCompleted(int orderId) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE orders SET status='COMPLETED' WHERE id=?");
            ps.setInt(1, orderId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override public Order findById(int id) {
        return null;
    }
    @Override public java.util.List<Order> findAll() {
        return java.util.List.of();
    }
    @Override public void save(Order entity) {

    }
    @Override public void update(Order entity) {

    }
    @Override public void delete(int id) {

    }
}
