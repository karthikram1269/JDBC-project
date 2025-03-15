package hotel_dao;

import java.io.FileInputStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import hotel_controller.UI;
import hotel_dto.LoginDs;


public class ProfileOperations {

static HotelOperations hs = new HotelOperations();
	Connection con;

	public void createManagerTable() {

		try {
			Properties p = new Properties();
			p.load(new FileInputStream("config.properties"));

			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/hotel_managing_project?createDatabaseIfNotExist=true", p);

			Statement sta = con.createStatement();

			sta.execute(
					"create table if not exists hotel_managers(email varchar(45) primary key ,name varchar(45), age int,phone bigint ,password varchar(45))");

			sta.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int fetch(String email) throws SQLException {
		createManagerTable();

		PreparedStatement ps = con.prepareStatement("select * from hotel_managers where email = ?");

		ps.setString(1, email);

		ResultSet rs = ps.executeQuery();
		int i = 0;

		while(rs.next()) {
			i = 1;
		}
		return i;
	}

	public int updateProfileDetails(LoginDs ld) {
		int i = 0;
		createManagerTable();
		try {
			PreparedStatement ps = con.prepareStatement(
					"update hotel_managers set name = ?, age = ?, phone = ?, password = ? where email = ?");

			if (fetch(UI.LoginEmail) == 0) {
				System.err.println(" email not found ");
				return i;
			}
			ps.setString(5, UI.LoginEmail);
			ps.setString(1, ld.getName());
			ps.setInt(2, ld.getAge());
			ps.setLong(3, ld.getPhone());
			ps.setString(4, ld.getPassword());

			i = ps.executeUpdate();

			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public int deleteProfile(LoginDs ld) {
		int i = 0;
		createManagerTable();
		try {
			PreparedStatement ps = con.prepareStatement("delete from hotel_managers where email = ?");
			ps.setString(1, ld.getEmail());

			i = ps.executeUpdate();

			PreparedStatement ss1 = con.prepareStatement("select * from hotel where email = ?");
			ss1.setString(1, ld.getEmail());
			ResultSet rs1 = ss1.executeQuery();

			while (rs1.next()) {
				int id1 = rs1.getInt("id");

				PreparedStatement ps2 = con.prepareStatement("delete from food_items where hotel_id = ?");

				ps2.setInt(1, id1);

				ps2.execute();
			}

			PreparedStatement ps1 = con.prepareStatement("delete from hotel where email = ?");
			ps1.setString(1, ld.getEmail());

			ps1.execute();

			ps1.close();
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public int profileDisplay(String email) {
		int i = 0;
		createManagerTable();
		hs.createHotelTable();
		try {

			if (fetch(email) == 0) {
				System.err.println(" email not found ");
				return i;
			}
			PreparedStatement ps = con.prepareStatement("select * from hotel_managers where email = ?");
			ps.setString(1,email);

			ResultSet rs = ps.executeQuery();

			if(rs.next()) {
				System.err.println("\n Email : " + rs.getString("email") + "\n Name : " + rs.getString("name") + "\n Age : "
						+ rs.getInt("age") + "\n Phone : " + rs.getLong("phone"));
			}

			PreparedStatement ss1 = con.prepareStatement("select * from hotel where email = ?");
			ss1.setString(1, email);
			
			ResultSet rs1 = ss1.executeQuery();

			while(rs1.next()) {
				System.out.println("\n\n Hotels of "+email+" are !!!!!\n");
				
				System.err.println("\n Hotel Id : " +rs1.getInt("id")+"\n Name : " + rs1.getString("name") + "\n Address : "
						 +rs1.getString("address"));
			}

			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
}
