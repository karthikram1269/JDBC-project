package hotel_controller;

import java.sql.SQLException;
import java.util.Scanner;
import hotel_dao.FoodItemsOperations;
import hotel_dao.HotelOperations;
import hotel_dto.FoodItems;
import hotel_dto.Hotel;

public class Login {

	static Hotel ht = new Hotel();
	static Profile pf = new Profile();
	static HotelsUI htl = new HotelsUI();
	static FoodItems fi = new FoodItems();
	static FoodItemsUI fiu = new FoodItemsUI();
	static HotelOperations ho = new HotelOperations();
	static FoodItemsOperations fo = new FoodItemsOperations();
	static Scanner sc = new Scanner(System.in);

	public boolean loginProfile() throws SQLException {
		boolean out = true;

		while (out) {
			System.out.println(" click : \n  1.Hotel\n  2.Food-items\n  3.Profile\n  4.Exit");

			int enter = sc.nextInt();

			switch (enter) {
			case 1: {
				htl.hotelMethod();
				}
				break;
			case 2: {
				fiu.foodItemsMethod();
			}
				break;
			case 3: {
				boolean b = pf.profileMethod();
				if(b == false) return false;
			}
				break;
			case 4: {
				System.out.println("Exit");
				out = false;
			}
				break;

			default:
				System.out.println(" invalid input ");
			}
		}
		return out;
	}
}
