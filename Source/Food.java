
public class Food {
	private final String name;
	private double price;
	private int number_sold;
	
	public Food(String name, double p) {
		this.name = name;
		this.price = p;
		this.number_sold = 0;
	}
	
	public String getName() {
		return name;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void sell_one() {
		number_sold++;
	}
	
	public int getNumberSold() {
		return number_sold;
	}
}
