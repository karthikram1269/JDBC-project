package hotel_dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import hotel_dto.Hotel;
import hotel_dto.LoginDs;

public class LoginOperations {

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

	public int register(LoginDs ld) {
		int i = 0;
		createManagerTable();
		try {
			PreparedStatement ps = con.prepareStatement("insert into hotel_managers values(?,?,?,?,?)");

			if (fetch(ld.getEmail()) == 1) {
				System.err.println(" email already exists ");
				return i;
			}
			ps.setString(1, ld.getEmail());
			ps.setString(2, ld.getName());
			ps.setInt(3, ld.getAge());
			ps.setLong(4, ld.getPhone());
			ps.setString(5, ld.getPassword());

			i = ps.executeUpdate();

			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public int fetch(String email) throws SQLException {
		createManagerTable();

		PreparedStatement ps = con.prepareStatement("select * from hotel_managers where email = ?");

		ps.setString(1, email);

		ResultSet rs = ps.executeQuery();
		int i = 0;

		if (rs.next())
			i = 1;

		return i;
	}

	public int login(LoginDs ld) throws SQLException {

		createManagerTable();

		PreparedStatement ps = con.prepareStatement("select password from hotel_managers where email = ?");

		ps.setString(1, ld.getEmail());

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			if (ld.getPassword().equals(rs.getString("password"))) {
				return 1;
			}
			rs.close();
			con.close();
			return 0;
		}
		return -1;
	}

	public void update(LoginDs ld) throws SQLException {
		createManagerTable();

		PreparedStatement ps = con.prepareStatement("update hotel_managers set password = ?  where email = ?");

		ps.setString(1, ld.getPassword());
		ps.setString(2, ld.getEmail());

		ps.execute();
		ps.close();
		con.close();
	}

}
