import java.util.*;

class Order{  // create Order class
    // Attributes of the Order class
	public String orderId;
	public String customerId;
	public String customerName;
	public int status;
	public int quantity;
	public double value;
	
	
	public Order(String orderId,String customerId,String customerName,int status,int quantity,double value){
		this.orderId=orderId;
		this.customerId=customerId;
		this.customerName=customerName;
		this.status=status;
		this.quantity=quantity;
		this.value=value;
		}
	}
		

	public class BurgerShop {
    public static final double BURGERPRICE = 500;
    public static final int CANCEL = 0;
    public static final int PREPARING = 1;
    public static final int DELIVERED = 2;

    // Object array එක (Order[])
    public static Order[] orders = new Order[100]; 
    public static int orderCount = 0;

    public static String generateOrderId() {
        if (orderCount == 0) return "B0001";
        String lastOrderId = orders[orderCount - 1].orderId;
        int number = Integer.parseInt(lastOrderId.substring(1));
        number++;
        return String.format("B%04d", number);
    }

    public static void placeOrder() {
        Scanner input = new Scanner(System.in);
        System.out.print("ORDER ID - ");
        String orderId = generateOrderId();
        System.out.println(orderId);

        System.out.print("Enter Customer ID: ");
        String customerId = input.next();
        System.out.print("Enter Customer Name: ");
        String customerName = input.next();
        System.out.print("Enter Burger Quantity: ");
        int qty = input.nextInt();
        double value = qty * BURGERPRICE;

        // Object ekak hadala array eke daanna
        orders[orderCount] = new Order(orderId, customerId, customerName, PREPARING, qty, value);
        orderCount++;
        System.out.println("Order placed successfully!");
    }

    public static void listOrders() {
        System.out.println("Order List:");
        for (int i = 0; i < orderCount; i++) {
            System.out.println(
                orders[i].orderId + " | " +
                orders[i].customerName + " | " +
                orders[i].quantity + " | " +
                orders[i].value + " | " +
                (orders[i].status == PREPARING ? "Preparing" : orders[i].status == DELIVERED ? "Delivered" : "Cancel")
            );
        }
    }
    
    	public static void main(String args[]){
		placeOrder();
        listOrders();
	
	}
}

