package Repository_pack;

import Entity_pack.MenuItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemRep implements Repository<MenuItem> {
    private final Connection connection;

    public MenuItemRep() {
        try { connection = DatabaseConnection.getConnection(); }
        catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public MenuItem findById(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM menu_items WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return new MenuItem(rs.getInt("id"), rs.getString("name"), rs.getDouble("menu_price"), rs.getBoolean("available"));
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public List<MenuItem> findAll() {
        List<MenuItem> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM menu_items");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(new MenuItem(rs.getInt("id"), rs.getString("name"), rs.getDouble("menu_price"), rs.getBoolean("available")));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override public void save(MenuItem entity) {

    }
    @Override public void update(MenuItem entity) {

    }
    @Override public void delete(int id) {

    }
}
