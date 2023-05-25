package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import factory.RestaurantDao;
import model.Categoria;
import model.Menu;

public class RestaurantRepository {

    private Connection conn;

    public RestaurantRepository() {
        this.conn = RestaurantDao.getConnection();
    }

    public List<Menu> getAll() {

        String sql = "SELECT m.Id, m.Name, m.Price, c.Nome as categoria From menu m "
                + "Inner Join categorias c ON c.Id = m.category_id";

        List<Menu> menu = new ArrayList<Menu>();

        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                var categoria = new Categoria();
                categoria.setNome(result.getString("categoria"));
                menu.add(new Menu(result.getInt("Id"), result.getString("name"), result.getDouble("price"),
                        categoria));
            }

            return menu;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }



    }

}
