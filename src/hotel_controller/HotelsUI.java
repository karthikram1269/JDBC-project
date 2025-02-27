package hotel_controller;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import hotel_dao.FoodItemsOperations;
import hotel_dao.HotelOperations;
import hotel_dto.FoodItems;
import hotel_dto.Hotel;

public class HotelsUI {


	static Hotel ht = new Hotel();
	static FoodItems fi = new FoodItems();
	static HotelOperations ho = new HotelOperations();
	static FoodItemsOperations fo = new FoodItemsOperations();
	static Scanner sc = new Scanner(System.in);
	
	public void hotelMethod() throws SQLException {
		boolean hote = true;

		while (hote) {
			System.out.println(
					" click : \n  1.Create Hotel\n  2.UpdateHotel\n  3.DeleteHotel\n  4.FetchHotel\n  5.FetchAllHisHotels\n  6.Exit\n");

			int createl = sc.nextInt();

			switch (createl) {
			case 1: {

				System.out.println("Enter id : ");
				int id = sc.nextInt();
				ht.setId(id);
				sc.nextLine();

				System.out.println("Enter name : ");
				String name = sc.nextLine();
				ht.setName(name);

				System.out.println("Enter address : ");
				String address = sc.nextLine();
				ht.setAddress(address);
				
				ht.setEmail(UI.LoginEmail);
				
				int i = ho.createHotel(ht);
				if(i == 1) System.err.println(" created hotel");
				else System.err.println(" hotel not created ");
			}
				break;
			case 2: {
				boolean up = true;
				while (up) {
					System.out.println(
							" click : \n  1.Update Hotel name\n  2.Update Hotel Address\n  3.Exit\n");
					int i = sc.nextInt();
					switch (i) {
					case 1: {
						System.out.println("Enter id : ");
						int id = 0;
						try {
							 id = sc.nextInt();
							sc.nextLine();
						}catch (InputMismatchException e) {
							System.err.println(" input mis-match, please provide int type input !!");
							sc.nextLine();
							id = sc.nextInt();
						}
						ht.setId(id);
						sc.nextLine();

						System.out.println("Give new hotel name : ");
						String name = sc.nextLine();
						ht.setName(name);
						ht.setEmail(UI.LoginEmail);
						int rs = ho.updateHotelName(ht);
						System.out.println((rs == 1) ? " updated name " : " cannot update name ");

					}
						break;
					case 2: {
						System.out.println("Enter id : ");
						int id = 0;
						try {
							 id = sc.nextInt();
							sc.nextLine();
						}catch (InputMismatchException e) {
							System.err.println(" input mis-match, please provide int type input !!");
							sc.nextLine();
							id = sc.nextInt();
						}
						ht.setId(id);
						sc.nextLine();

						System.out.println("Give new address : ");
						String address = sc.nextLine();
						ht.setAddress(address);
						ht.setEmail(UI.LoginEmail);

						int rs = ho.updateHotelAddress(ht);
						System.out.println((rs == 1) ? " updated address " : " cannot update address ");
					}
						break;
					case 3: {
						System.out.println(" exitted from  updating hotels ");
						up = false;
					}
						break;
					default: {
						System.err.println(" invalid input ");
					}
					}
				}
			}
				break;
			case 3: {
				System.out.println("Enter id : ");
				int id = sc.nextInt();
				ht.setId(id);
				sc.nextLine();
				ht.setEmail(UI.LoginEmail);

				int i = ho.deleteHotel(ht);				
				if(i > 0) System.err.println("\n hotel deleted \n");
			}
				break;
			case 4: {
				System.out.println("Enter id : ");
				int id = sc.nextInt();
				ht.setId(id);
				sc.nextLine();

				int co = ho.fetchHotel(ht);
				if (co == 0) {
					System.out.println(" invalid input ");
				}else {
					System.err.println("\n hotel fetched successfully \n");			
				}
			}
				break;
			case 5: {

				boolean fa = ho.fetchAllHotelsOfUser(ht);
				if (fa) {
					System.out.println("\n fetched all hotels successfuly \n");
				} else {
					System.err.println(" no data present in table");
				}
			}
				break;
				
			case 6: {
				hote = false;
				System.out.println(" exitting from login db ");
			}
				break;

			default:
				System.err.println(" invalid input ");
			}
		}

	}
}
