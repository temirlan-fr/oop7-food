package Repository_pack;

import Entity_pack.MenuItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MenuItemRep implements Repository<MenuItem> {

    private final Connection connection;

    public MenuItemRep() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to DB", e);
        }
    }

    public MenuItem findById(int id) {
        String sql = "SELECT * FROM menu_items WHERE id=?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                MenuItem item = new MenuItem();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getDouble("menu_price"));
                item.setAvailable(rs.getBoolean("available"));
                return item;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<MenuItem> findAll() {
        return List.of();
    }

    @Override
    public void save(MenuItem entity) {

    }
}
