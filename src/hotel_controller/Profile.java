package hotel_controller;

import java.util.Scanner;

import hotel_dao.ProfileOperations;
import hotel_dto.LoginDs;

public class Profile {
	
	static LoginDs ld = new LoginDs();
	static ProfileOperations po = new ProfileOperations();
	static Scanner sc = new Scanner(System.in);

	public boolean profileMethod() {

		boolean out = true;

		while (out) {
			System.out.println(" click : \n  1.Update\n  2.Delete\n  3.Profile Display\n  4.Exit");

			int enter = sc.nextInt();

			switch (enter) {
				case 1: {
					
					sc.nextLine();
					System.out.println("Enter new password : ");
					String pwd = sc.nextLine();
					ld.setPassword(pwd);
					
					System.out.println("Enter new name : ");
					String name = sc.nextLine();
					ld.setName(name);
					
					System.out.println("Enter new age : ");
					int age = sc.nextInt();
					ld.setAge(age);
					sc.nextLine();
					
					System.out.println("Enter new phone : ");
					long phone = sc.nextLong();
					ld.setPhone(phone);
					sc.nextLine();

					int i = po.updateProfileDetails(ld);
					System.out.println(i == 1 ? "data added" : "data not added");
				}
				break;
				
				case 2: {
					
					ld.setEmail(UI.LoginEmail);
					
					int i = po.deleteProfile(ld);
					System.out.println(i +" row(s) deleted ");
					if(i > 0) {
						return false;
					}
				}
				break;
				
				case 3: {
					ld.setEmail(UI.LoginEmail);
					
					int i = po.profileDisplay(UI.LoginEmail);
//					System.out.println(i +" row(s) deleted ");
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
