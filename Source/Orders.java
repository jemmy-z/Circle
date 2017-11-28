import java.util.ArrayList;

public class Orders {
	private String name;
	private ArrayList<Food> orders;
	private ArrayList<Integer> numbers;
	private boolean status;
	private String time;
	private double total_price;
	private String order_number;
	
	public Orders(String na, ArrayList<Food> fl, ArrayList<Integer> n, String t, String on) {
		this.name = na;
		this.orders = fl;
		this.numbers = n;
		this.status = false;
		this.time = t;
		this.order_number = on;
		calcCost();
		
	}
	
	private void calcCost() {
		total_price = 0.0;
		for(int i = 0; i < orders.size(); i++) {
			total_price += orders.get(i).getPrice() * numbers.get(i);
		}
	}
	
	public String getCustomerName() {
		return name;
	}
		
	public int getOrderCount(Food f) {
		int index = orders.indexOf(f);
		return numbers.get(index);
	}
	
	public void completeOrder() {
		status = true;
	}
	
	public void setStatus(boolean s) {
		status = s;
	}
	public boolean getStatus() {
		return status;
	}

	public double getTotalCost() {
		return total_price;
	}
	
	public String getOrderNumber() {
		return order_number;
	}
	
	public String toString() {
		String r = time + ",";
		r += order_number + ",";
		r += name + ",";
		for(Integer i: numbers) {
			r += i.toString() + ",";
		}
		r += getStatus();
		
		return r;
	}
}
