package hotel_dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import hotel_controller.UI;
import hotel_dto.FoodItems;

public class FoodItemsOperations {

	Connection con;

	public int fetchFoodItemId(int id) throws SQLException {
		int i = 0;

		createFoodItemTable();

		PreparedStatement ps = con.prepareStatement("select * from food_items where hotel_id = ?");

		ps.setInt(1, id);

		ResultSet rs = ps.executeQuery();

		if (rs.next())
			i++;

		ps.close();
		con.close();

		return i;
	}

	public int fetchFoodItemId(FoodItems fi) throws SQLException {
		int i = 0;

		createFoodItemTable();

		PreparedStatement ps = con.prepareStatement("select * from food_items where hotel_id = ? and item = ?");

		if (fetchFoodItemId(fi.getId()) == 0) {
			System.err.println(" invalid id ");
			return -1;
		}

		ps.setInt(1, fi.getId());
		ps.setString(2, fi.getName());

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			System.out.println(" Id : " + rs.getInt("hotel_id") + "\n Item : " + rs.getString("item")
					+ "\n Item Price : " + rs.getDouble("item_price"));
			i++;
		}
		ps.close();
		con.close();

		return i;
	}

	public int checkDuplicate(int id, String name) throws SQLException {
		int i = 0;

		createFoodItemTable();

		PreparedStatement ps = con.prepareStatement("select * from food_items where hotel_id = ? and item = ?");

		ps.setInt(1, id);
		ps.setString(2,name);
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) i++;
		
		return i;
	}

	public int fetchUserHotelIds(int id) {
		int i = 0;
		createFoodItemTable();
		try {
			PreparedStatement ps = con.prepareStatement("select * from hotel where email = ? and id = ?");

			ps.setString(1, UI.LoginEmail);
			ps.setInt(2, id);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if (rs.getInt("id") == id) {
					i++;
				}
			}
			
		} catch (SQLException e) {
			System.err.println(" invalid id ");
		}
		return i;
	}

	public int addFoodItem(FoodItems fi) {
		int ui = 0;
		try {
			createFoodItemTable();

			PreparedStatement ps = con.prepareStatement("insert into food_items values(?,?,?)");

			if (fetchUserHotelIds(fi.getId()) > 0 ) {
				ps.setInt(3, fi.getId());
			} else {
				System.err.println(" invalid id ");
				return 0;
			}
			if (checkDuplicate(fi.getId(),fi.getName()) == 0) {
				ps.setInt(3, fi.getId());
			} else {
				System.err.println(fi.getName()+" already exists in hotel "+fi.getId());
				return 0;
			}

			ps.setString(1, fi.getName());
			ps.setDouble(2, fi.getprice());

			ui = ps.executeUpdate();
			ps.close();
			con.close();
		} catch (Exception e) {
			System.err.println(" invalid id ");
		}
		return ui;
	}

	public int updateFoodItem(FoodItems fi) {
		int i = 0;
		try {
			createFoodItemTable();

			PreparedStatement ps = con
					.prepareStatement("update food_items set item_price = ? where item = ? and hotel_id = ?");

			if (fetchUserHotelIds(fi.getId()) == 1) {
				ps.setInt(3, fi.getId());
			} else {
				System.err.println(" invalid id ");
			}
			if (checkDuplicate(fi.getId(),fi.getName()) == 0) {
				ps.setString(2, fi.getName());
			} else {
				System.err.println(fi.getName()+" already exists in hotel "+fi.getId());
				return 0;
			}

			ps.setDouble(1, fi.getprice());

			i = ps.executeUpdate();
			ps.close();
			con.close();

			System.err.println(i + " row(s) updated successfully !!! ");

		}
		catch (Exception e) {
			System.err.println(" invalid input ");
		}
		
		return i;

	}

	public int deleteFoodItem(FoodItems fi){

		int i = 0;
		try {
			createFoodItemTable();
			PreparedStatement ps = con.prepareStatement("delete from food_items where hotel_id = ? and item = ?");

			if (fetchUserHotelIds(fi.getId()) > 0) {
				ps.setInt(1, fi.getId());
			} else {
				System.err.println(" invalid id ");
			}
			
			if (checkDuplicate(fi.getId(),fi.getName()) == 1) {
				ps.setString(2, fi.getName());
			} else {
				System.err.println(fi.getName()+" already exists in hotel "+fi.getId());
				return 0;
			}

			int d = ps.executeUpdate();

			ps.close();
			con.close();

			if (d > 0) {
				i++;
				System.out.println(i + " row(s) deleted successfully");
				return 1;
			} else {

				System.err.println(" invalid name ");
				return 0;
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return i;
	}

	public void createFoodItemTable() {
		try {
			Properties p = new Properties();
			p.load(new FileInputStream("config.properties"));

			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/hotel_managing_project?createDatabaseIfNotExist=true", p);

			Statement sta = con.createStatement();
			sta.execute(
					"create table if not exists food_items(item varchar(45), item_price DECIMAL(10,2), hotel_id INT)");
		} catch (IOException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
