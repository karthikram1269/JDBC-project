package hotel_controller;

import java.sql.SQLException;
import java.util.Scanner;

import hotel_dao.FoodItemsOperations;
import hotel_dao.HotelOperations;
import hotel_dto.FoodItems;
import hotel_dto.Hotel;

public class FoodItemsUI {


	static Hotel ht = new Hotel();
	static HotelsUI htl = new HotelsUI();
	static FoodItems fi = new FoodItems();
	static HotelOperations ho = new HotelOperations();
	static FoodItemsOperations fo = new FoodItemsOperations();
	static Scanner sc = new Scanner(System.in);
	
	
	
	public void foodItemsMethod() throws SQLException {
		boolean food = true;
		
		while(food) {
			System.out.println(
					" click : \n  1.Add Food_item\n  2.Update Food_item\n  3.Delete Food_item\n  4.Fetch_FoodItemsBasedonHotel\n  5.Exit\n");
			int f = sc.nextInt();
			
			switch(f) {
				case 1 : {
					System.out.println("Enter hotel id : ");
					int hotel_id = sc.nextInt();
					sc.nextLine();
					
					fi.setId(hotel_id);
					
					System.out.println("Enter item name : ");
					String item = sc.nextLine();
					fi.setName(item);
					
					System.out.println("Enter price : ");
					double price = sc.nextDouble();
					sc.nextLine();
					fi.setPrice(price);
					
					fo.addFoodItem(fi);
					System.err.println("\n food item added \n");
				}
				break;
				case 2 : {
					System.out.println(" for Updating food item price !!!! ");
					
					System.out.println("Enter hotel id : ");
					int hotel_id = sc.nextInt();
					sc.nextLine();
					fi.setId(hotel_id);
				
					System.out.println("Enter item name : ");
					String item = sc.nextLine();
					fi.setName(item);
					
					System.out.println("Enter new price : ");
					double price = sc.nextDouble();
					sc.nextLine();
					fi.setPrice(price);
					
					int s = fo.updateFoodItem(fi);
					if(s > 0)System.err.println("\n food item updated \n");
				}
				break;
				case 3 : {
					
					System.out.println("Enter hotel id : ");
					int hotel_id = sc.nextInt();
					sc.nextLine();
					fi.setId(hotel_id);

					System.out.println("Enter item name : ");
					String item = sc.nextLine().trim();
					fi.setName(item);
					
					int i = fo.deleteFoodItem(fi);
					if(i > 0 ) System.err.println("\n food item deleted \n");
				}
				break;
				case 4 : {
					System.out.println(" fetch food item based on hotel(id) and name !!!! ");
					System.out.println("Enter hotel id : ");
					int hotel_id = sc.nextInt();
					sc.nextLine();
					fi.setId(hotel_id);
					
					System.out.println("Enter item name : ");
					String item = sc.nextLine().trim();
					fi.setName(item);
					
					int i = fo.fetchFoodItemId(fi);
					if(i == 1)System.err.println("\n food item found \n");
					else System.err.println("\n no food item found \n");
				}
				break;
				case 5 : {
					System.out.println(" exited from food item ");
					food = false;
				}
				break;
				
				default : System.err.println(" invalid input of food_item operation ");
			}
		}
	}
}
