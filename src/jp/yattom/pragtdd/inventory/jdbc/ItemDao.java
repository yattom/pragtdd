package jp.yattom.pragtdd.inventory.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jp.yattom.pragtdd.inventory.Item;

public class ItemDao {

	public Item findByName(String name) throws SQLException {
		try (Connection con = connect()) {
			PreparedStatement stmt = con
					.prepareStatement("SELECT * FROM items WHERE name = ?");
			stmt.setString(1, name);
			ResultSet result = stmt.executeQuery();
			if (!result.next()) {
				return null;
			}
			String resultName = result.getString("name");
			Item item = new Item(resultName);
			return item;
		}
	}

	public void save(Item item) throws SQLException {
		try (Connection con = connect()) {
			PreparedStatement stmt = con
					.prepareStatement("INSERT INTO items (name) VALUES (?)");
			stmt.setString(1, item.getName());
			stmt.execute();
		}
	}

	public void createTable() throws SQLException {
		try (Connection con = connect()) {
			Statement stmt = con.createStatement();
			stmt.execute("CREATE TABLE items (name VARCHAR(256))");
		}
	}

	private Connection connect() throws SQLException {
		Connection con = DriverManager
				.getConnection("jdbc:derby:testdb;create=true");
		return con;
	}

	public void dropTable() throws SQLException {
		try (Connection con = connect()) {
			Statement stmt = con.createStatement();
			stmt.execute("DROP TABLE items");
		}
	}

}
