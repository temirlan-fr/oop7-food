package Repository_pack;

import Entity_pack.MenuItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MenuItemRep {

    public MenuItem findById(int id) {
        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM menu_items WHERE id = ?");
            ps.setInt(1, id)

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new MenuItem(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getBoolean("available")
                );
            }
            return null;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
