package hotel_controller;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import hotel_dao.LoginOperations;
import hotel_dto.LoginDs;

public class UI {

	static Scanner sc = new Scanner(System.in);
	static Login l = new Login();
	static LoginOperations lo = new LoginOperations();
	public static String LoginEmail = "";

	public static void main(String[] args) throws SQLException {

		System.out.println(" Welcome to the Hotel Managing application !!!\n");

		boolean stay = true;

		while (stay) {
			System.out.println(" click : \n  1.Register\n  2.Login\n  3.Exit\n");
			int click = 0;
			try {
				click = sc.nextInt();
				sc.nextLine(); // Consume the newline character
			} catch (InputMismatchException e) {
				System.out.println("Invalid input! Provide an integer type input.");
				sc.nextLine(); // Clear invalid input before retrying
				continue; // Restart loop to ask for input again
			}

			switch (click) {
			case 1: {
				LoginDs ld = new LoginDs();

				sc.nextLine();
				System.out.println("Enter name : ");
				String name = sc.nextLine();
				ld.setName(name);

				System.out.println("Enter age : ");
				int age = sc.nextInt();
				ld.setAge(age);
				sc.nextLine();

				System.out.println("Enter phone : ");
				long phone = sc.nextLong();
				ld.setPhone(phone);
				sc.nextLine();

				System.out.println("Enter email : ");
				String email = sc.nextLine();
				ld.setEmail(email);

				// kar21@com

				System.out.println("Enter password : ");
				String pwd = sc.nextLine();
				ld.setPassword(pwd);

				int i = lo.register(ld);
				System.out.println(i == 1 ? "data added" : "data not added");
			}
				break;

			case 2: {
				LoginDs ld = new LoginDs();

				System.out.println("Enter email : ");
				LoginEmail = sc.next();
				ld.setEmail(LoginEmail);

				System.out.println("Enter password : ");
				String pwd = sc.next();
				ld.setPassword(pwd);

				int c = lo.login(ld);

				switch (c) {
				case 1: {
					System.err.println(" Login successful !!");
					l.loginProfile();
				}
					break;

				case -1: {
					System.out.println(" invalid mail");
				}
					break;

				case 0: {
					System.out.println(" invalid password");
				}
					break;
				}
			}
				break;

			case 3: {
				stay = false;
				System.out.print(" Chosen exit. See you next time.");
				System.err.println(" Thank you !!");
			}
				break;

			default:
				System.out.println(" invalid input ");
			}
		}

	}
}
