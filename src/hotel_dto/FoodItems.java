package hotel_dto;

public class FoodItems {
	private String name;
	private double price;
	private int id;
	
	public FoodItems () {}
	
	public FoodItems(String name, double price, int id) {
		super();
		this.name = name;
		this.price = price;
		this.id = id;
	}
	@Override
	public String toString() {
		return "Foodnames [name=" + name + ", price=" + price + ", id=" + id + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getprice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
