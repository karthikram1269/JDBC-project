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
import hotel_dto.Hotel;

public class HotelOperations {
	Connection con;
	public void createHotelTable() {

		try {
			Properties p = new Properties();
			p.load(new FileInputStream("config.properties"));

			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_managing_project?createDatabaseIfNotExist=true", p);

			Statement sta = con.createStatement();

			sta.execute("create table if not exists hotel(id int primary key, name varchar(45), address varchar(45) ,email varchar(45))");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int createHotel(Hotel ht)  {
		
		int c = 0;
		try {
			createHotelTable();

			PreparedStatement ps = con.prepareStatement("insert into hotel values(?,?,?,?)");

			ps.setInt(1, ht.getId());
			ps.setString(2, ht.getName());
			ps.setString(3, ht.getAddress());
			ps.setString(4, ht.getEmail());

			c = ps.executeUpdate();

			ps.close();
			con.close();
		}
		catch (Exception e) {
			System.err.println(" duplicate entry ");
		}
		return c;
	}
	
	public int fetchHotelBasedOnId(int id) {
		
		int i = 0;
		try {
			createHotelTable();
			PreparedStatement ps = con.prepareStatement("select * from hotel where id = ? and email = ?");
			
			ps.setInt(1, id);
			ps.setString(2, UI.LoginEmail);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) i++;
		}
		catch (Exception e) {
			System.err.println(" invalid input ");
		}
		return i;
	}

	public int updateHotelName(Hotel ht) {
		try {
			createHotelTable();
			PreparedStatement ps = con.prepareStatement("update hotel set name = ? where id = ? and email = ?");
			
			if(fetchHotelBasedOnId(ht.getId()) == 0) {
				System.err.println(" invalid id ");
				return 0;
			}
			
			ps.setString(1, ht.getName());
			ps.setInt(2, ht.getId());
			ps.setString(3, ht.getEmail());

			ps.execute();

			ps.close();
			con.close();
		}
		catch (Exception e) {
			System.err.println(" invalid input ");
		}
		return 1;
	}

	public int updateHotelAddress(Hotel ht) {
		try {
			createHotelTable();
			PreparedStatement ps = con.prepareStatement("update hotel set address = ? where id = ? and email = ?");

			if(fetchHotelBasedOnId(ht.getId()) == 0) {
				System.err.println(" invalid id ");
				return 0;
			}
			
			ps.setInt(2, ht.getId());
			ps.setString(1, ht.getAddress());
			ps.setString(3, ht.getEmail());

			ps.execute();

			ps.close();
			con.close();
		}
		catch (Exception e) {
			System.err.println(" invalid input ");
		}
		return 1;
	}

	public int deleteHotel(Hotel ht) {
		int i = 0;
		try {
			createHotelTable();
			PreparedStatement ps = con.prepareStatement("delete from hotel where id = ? and email = ?");

			if(fetchHotelBasedOnId(ht.getId()) == 0) {
				System.err.println(" invalid id ");
				return 0;
			}
			ps.setInt(1, ht.getId());
			ps.setString(2, ht.getEmail());
			i = ps.executeUpdate();

			PreparedStatement ps2 = con.prepareStatement("delete from food_items where hotel_id = ?");
			ps2.setInt(1, ht.getId());
			ps2.execute();
			
			ps.close();
			ps2.close();
			con.close();
		}
		catch (Exception e) {
			System.err.println(" invalid input ");
		}
		return i;
	}

	public int fetchHotel(Hotel ht) {
		int count = 0;
		try {
			createHotelTable();
			PreparedStatement ps = con.prepareStatement("select * from hotel where id = ? and email = ?");

			ps.setInt(1, ht.getId());
			ps.setString(2, UI.LoginEmail);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) 
			{
				System.err.println(" Id : " + rs.getInt(1) + "\n Name : " + rs.getString(2) + "\n Address : " + rs.getString(3)
						+ "\n Email : " + UI.LoginEmail);
				count++;
			}
			ps.close();
		}
		catch (Exception e) {
			System.err.println(" invalid id ");
		}
		return count;
	}

	public boolean fetchAllHotelsOfUser(Hotel ht) {
		
		boolean cu = false;
		try {
			createHotelTable();
			PreparedStatement ps = con.prepareStatement("select * from hotel where email = ?");

			ps.setString(1, UI.LoginEmail);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) 
			{

				System.err.println(" Id : " + rs.getInt(1) + "\n Name : " + rs.getString(2) + "\n Address : " + rs.getString(3)
						+ "\n Email : " + rs.getString(4) + " \n");
				cu = true;
			}
			ps.close();
		}
		catch (Exception e) {
			System.err.println(" invalid input ");
		}
		return cu;
	}
}
