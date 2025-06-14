import java.util.*;
class InnerBurgerShop {

    
}

class Order { // create Order class
    // Attributes of the Order class
    private String orderId;
    private String customerId;
    private String customerName;
    private int status;
    private int qty;
    private double value;

    public Order(String orderId, String customerId, String customerName, int status, int qty, double value) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.status = status;
        this.qty = qty;
        this.value = value;
    }

    // Getters
    public String getOrderId() {
		 return orderId; 
		 }
    public String getCustomerId() {
		 return customerId;
		 
		  }
    public String getCustomerName() { return customerName; }
    public int getStatus() { return status; }
    public int getQty() { return qty; }
    public double getValue() { return value; }

    // Setters
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setStatus(int status) { this.status = status; }
    public void setQty(int qty) { this.qty = qty; }
    public void setValue(double value) { this.value = value; }
}

public class BurgerShop {

    final static double BURGERPRICE = 500;
    public static final int CANCEL = 0;
    public static final int PREPARING = 1;
    public static final int DELIVERED = 2;

    // Array of Order objects
    public static Order[] orders = {
        new Order("B0001", "0701111111", "Amali", 2, 1, 500),
        new Order("B0002", "0777777777", "Shehan", 1, 2, 1000),
        new Order("B0003", "0342222222", "Kasun", 2, 4, 2000),
        new Order("B0004", "0777777777", "Shehan", 2, 2, 1000),
        new Order("B0005", "0386677676", "Chathura", 1, 1, 500),
        new Order("B0006", "0715455465", "Ruwan", 1, 1, 500),
        new Order("B0007", "0709353956", "Rishmi", 0, 3, 1500),
        new Order("B0008", "0724565562", "Gihan", 2, 6, 3000),
        new Order("B0009", "0715455465", "Ruwan", 1, 5, 2500),
        new Order("B0010", "0342222222", "Dilshan", 1, 2, 1000),
        new Order("B0011", "0342233223", "Hasindu", 2, 7, 3500),
        new Order("B0012", "0755080123", "Eshan", 2, 3, 1500),
        new Order("B0013", "0715994936", "Ravindu", 1, 2, 1000),
        new Order("B0014", "0715994940", "Harshana", 0, 2, 1000),
        new Order("B0015", "0745994967", "Pasindu", 2, 1, 500)
    };

    public static void addOrder(Order order) {
        Order[] temp = new Order[orders.length + 1];
        for (int i = 0; i < orders.length; i++) {
            temp[i] = orders[i];
        }
        temp[orders.length] = order;
        orders = temp;
    }

    public static String generateOrderId() {
        if (orders.length == 0) return "B0001";
        String lastOrderId = orders[orders.length - 1].getOrderId();
        int number = Integer.parseInt(lastOrderId.substring(1));
        number++;
        return String.format("B%04d", number);
    }

    public static boolean validationCustomerId(String customerId) {
        if (customerId.length() == 10 && customerId.startsWith("0")) {
            try {
                Integer.parseInt(customerId);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    public static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            // ignore
        }
    }

    public static void placeOrder() {
        Scanner input = new Scanner(System.in);
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("|\t\t\t\tPLACE ORDER\t\t\t\t|");
        System.out.println("-------------------------------------------------------------------------\n\n");
        System.out.print("ORDER ID - ");
        String orderId = generateOrderId();
        System.out.println(orderId + "\n================\n\n");

        L1: do {
            System.out.print("Enter Customer ID (phone no.): ");
            String customerId = input.next();
            if (!validationCustomerId(customerId)) continue L1;

            boolean isExistCustomer = false;
            String customerName = "";
            for (Order order : orders) {
                if (customerId.equals(order.getCustomerId())) {
                    isExistCustomer = true;
                    customerName = order.getCustomerName();
                    System.out.println("Enter Customer Name: " + customerName);
                    break;
                }
            }
            if (!isExistCustomer) {
                System.out.print("\nEnter Customer Name: ");
                customerName = input.next();
            }
            System.out.print("Enter Burger Quantity - ");
            int qty = input.nextInt();
            if (qty > 0) {
                double billValue = qty * BURGERPRICE;
                System.out.printf("Total value - %.2f\n", billValue);

                L3: do {
                    System.out.print("\tAre you confirm order - ");
                    String option = input.next().toUpperCase();
                    if (option.equalsIgnoreCase("Y")) {
                        addOrder(new Order(orderId, customerId, customerName, PREPARING, qty, billValue));
                        System.out.println("\n\tYour order is enter to the system successfully...");
                        break L1;
                    } else if (option.equalsIgnoreCase("N")) {
                        System.out.println("\n\tYour order is not enter the system...");
                        clearConsole();
                        return;
                    } else {
                        System.out.println("\tInvalid option..input again...");
                        break L1;
                    }
                } while (true);
            }
        } while (true);

        L4: do {
            System.out.println();
            System.out.print("Do you want to place another order (Y/N): ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y")) {
                clearConsole();
                placeOrder();
            } else if (option.equalsIgnoreCase("N")) {
                clearConsole();
                homePage();
            } else {
                System.out.println("\tInvalid option..input again...");
                continue L4;
            }
        } while (true);
    }

    public static void searchBestCustomer() {
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("|\t\t\t\tBEST Customer\t\t\t\t|");
        System.out.println("-------------------------------------------------------------------------\n");

        String[] customerIds = new String[0];
        String[] customerNames = new String[0];
        double[] totals = new double[0];

        for (Order order : orders) {
            if (order.getStatus() == CANCEL) continue;
            boolean exists = false;
            for (int j = 0; j < customerIds.length; j++) {
                if (customerIds[j].equals(order.getCustomerId())) {
                    totals[j] += order.getValue();
                    exists = true;
                }
            }
            if (!exists) {
                String[] tempIds = new String[customerIds.length+1];
                String[] tempNames = new String[customerNames.length+1];
                double[] tempTotals = new double[totals.length+1];
                for (int j=0; j<customerIds.length; j++) {
                    tempIds[j] = customerIds[j];
                    tempNames[j] = customerNames[j];
                    tempTotals[j] = totals[j];
                }
                tempIds[customerIds.length] = order.getCustomerId();
                tempNames[customerNames.length] = order.getCustomerName();
                tempTotals[totals.length] = order.getValue();
                customerIds = tempIds;
                customerNames = tempNames;
                totals = tempTotals;
            }
        }

        for (int i=1; i<totals.length; i++) {
            for (int j=0; j<i; j++) {
                if (totals[j] < totals[i]) {
                    double t = totals[j]; totals[j] = totals[i]; totals[i] = t;
                    String id = customerIds[j]; customerIds[j] = customerIds[i]; customerIds[i] = id;
                    String n = customerNames[j]; customerNames[j] = customerNames[i]; customerNames[i] = n;
                }
            }
        }

        System.out.println("\n----------------------------------------");
        String line1 = String.format("%-14s%-15s%8s", " CustomerID", "Name", "Total");
        System.out.println(line1);
        System.out.println("----------------------------------------");
        for (int i = 0; i < customerIds.length; i++) {
            String line = String.format("%1s%-14s%-15s%8.2f", " ", customerIds[i], customerNames[i], totals[i]);
            System.out.println(line);
            System.out.println("----------------------------------------");
        }

        L: do {
            Scanner input = new Scanner(System.in);
            System.out.print("\n\tDo you want to go back to main menu? (Y/N)> ");
            String exitOption = input.nextLine();
            if (exitOption.equalsIgnoreCase("Y")) {
                clearConsole();
                homePage();
            } else if (exitOption.equalsIgnoreCase("N")) {
                clearConsole();
                searchBestCustomer();
            } else {
                System.out.println("\tInvalid option..input again...");
                continue L;
            }
        } while (true);
    }

    public static void searchOrder() {
        Scanner input = new Scanner(System.in);
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("|\t\t\t\tSEARCH ORDER DETAILS\t\t\t\t|");
        System.out.println("--------------------------------------------------------------------------------\n");
        L1: do {
            System.out.print("Enter order Id - ");
            String orderIdInput = input.next();
            System.out.println();
            boolean found = false;
            for (Order order : orders) {
                if (orderIdInput.equals(order.getOrderId())) {
                    String status = "";
                    switch (order.getStatus()) {
                        case CANCEL: status = "Cancel"; break;
                        case PREPARING: status = "Preparing"; break;
                        case DELIVERED: status = "Delivered"; break;
                    }
                    System.out.println("---------------------------------------------------------------------------");
                    String line1 = String.format("%-10s%-14s%-12s%-10s%-14s%-10s", " OrderID", " CustomerID", " Name",
                            "Quantity", "  OrderValue", "  OrderStatus");
                    System.out.print(line1);
                    System.out.println(" |");
                    System.out.println("---------------------------------------------------------------------------");
                    String line = String.format("%1s%-10s%-14s%-15s%-10d%-14.2f%-10s", " ", order.getOrderId(),
                            order.getCustomerId(), order.getCustomerName(), order.getQty(), order.getValue(), status);
                    System.out.print(line);
                    System.out.println("|");
                    System.out.println("---------------------------------------------------------------------------");
                    found = true;
                    break L1;
                }
            }
            if (!found) {
                L2: do {
                    System.out.print("\n\nInvalid Order ID. Do you want to enter again? (Y/N)>");
                    String option = input.next();
                    if (option.equalsIgnoreCase("Y")) {
                        clearConsole();
                        searchOrder();
                    } else if (option.equalsIgnoreCase("N")) {
                        clearConsole();
                        return;
                    } else {
                        System.out.println("\tInvalid option..input again...");
                        continue L2;
                    }
                } while (true);
            }
        } while (true);
        L3: do {
            System.out.println();
            System.out.print("Do you want to search another order details (Y/N): ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y")) {
                clearConsole();
                searchOrder();
            } else if (option.equalsIgnoreCase("N")) {
                clearConsole();
                homePage();
            } else {
                System.out.println("\tInvalid option..input again...");
                continue L3;
            }
        } while (true);
    }

    public static void searchCustomer() {
        Scanner input = new Scanner(System.in);
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("|\t\t\t\tSEARCH CUSTOMER DETAILS\t\t\t\t|");
        System.out.println("--------------------------------------------------------------------------------\n");
        L1: do {
            System.out.print("Enter customer Id - ");
            String customerIdInput = input.next();
            System.out.println("\n");
            boolean found = false;
            for (Order order : orders) {
                if (customerIdInput.equals(order.getCustomerId())) {
                    System.out.println("CustomerID - " + order.getCustomerId());
                    System.out.println("Name       - " + order.getCustomerName());
                    System.out.println("\nCustomer Order Details");
                    System.out.println("========================\n");
                    System.out.println("----------------------------------------------");
                    String line = String.format("%-12s%-18s%-14s", " Order_ID", "Order_Quantity", "Total_Value  ");
                    System.out.println(line);
                    System.out.println("----------------------------------------------");
                    for (Order o : orders) {
                        if (o.getCustomerId().equals(customerIdInput)) {
                            String line2 = String.format("%1s%-12s%-18d%-14.2f", " ", o.getOrderId(), o.getQty(), o.getValue());
                            System.out.println(line2);
                        }
                    }
                    System.out.println("----------------------------------------------");
                    found = true;
                    break L1;
                }
            }
            if (!found) {
                L2: do {
                    System.out.print("\n\nInvalid Customer ID. Do you want to enter again? (Y/N)>");
                    String option = input.next();
                    if (option.equalsIgnoreCase("Y")) {
                        clearConsole();
                        searchCustomer();
                    } else if (option.equalsIgnoreCase("N")) {
                        clearConsole();
                        return;
                    } else {
                        System.out.println("\tInvalid option..input again...");
                        continue L2;
                    }
                } while (true);
            }
        } while (true);
        L3: do {
            System.out.println();
            System.out.print("Do you want to search another customer details (Y/N): ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y")) {
                clearConsole();
                searchCustomer();
            } else if (option.equalsIgnoreCase("N")) {
                clearConsole();
                homePage();
            } else {
                System.out.println("\tInvalid option..input again...");
                continue L3;
            }
        } while (true);
    }

    public static void viewOrders() {
        Scanner input = new Scanner(System.in);
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("|\t\t\t\tVIEW ORDER LIST\t\t\t\t|");
        System.out.println("-------------------------------------------------------------------------\n");
        System.out.println("[1] Delivered Order");
        System.out.println("[2] Preparing Order");
        System.out.println("[3] Cancel Order");

        System.out.print("\nEnter an option to continue > ");
        int option = input.nextInt();
        switch (option) {
            case 1:
                clearConsole();
                deliverOrder();
                break;
            case 2:
                clearConsole();
                preparingOrder();
                break;
            case 3:
                clearConsole();
                cancelOrder();
                break;
        }
    }

    public static void deliverOrder() {
        Scanner input = new Scanner(System.in);
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("|\t\t\t\tDELIVERED ORDER\t\t\t\t\t|");
        System.out.println("--------------------------------------------------------------------------------\n");
        System.out.println("\n--------------------------------------------------------------");
        String line1 = String.format("%-10s%-15s%-13s%-10s%12s", " OrderID", " CustomerID", " Name", "Quantity",
                "  OrderValue");
        System.out.println(line1);
        System.out.println("--------------------------------------------------------------");
        for (Order order : orders) {
            if (order.getStatus() == DELIVERED) {
                String line = String.format("%1s%-10s%-15s%-15s%-10d%8.2f", " ", order.getOrderId(), order.getCustomerId(),
                        order.getCustomerName(), order.getQty(), order.getValue());
                System.out.println(line);
                System.out.println("--------------------------------------------------------------");
            }
        }
        L1: do {
            System.out.print("\nDo you want to go to home page (Y/N): ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y")) {
                clearConsole();
                homePage();
            } else if (option.equalsIgnoreCase("N")) {
                clearConsole();
                deliverOrder();
            } else {
                System.out.println("\tInvalid option..input again...");
                continue L1;
            }
        } while (true);
    }

    public static void preparingOrder() {
        Scanner input = new Scanner(System.in);
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("|\t\t\t\tPREPARING ORDER\t\t\t\t\t|");
        System.out.println("--------------------------------------------------------------------------------\n");
        System.out.println("\n--------------------------------------------------------------");
        String line1 = String.format("%-10s%-15s%-13s%-10s%12s", " OrderID", " CustomerID", " Name", "Quantity",
                "  OrderValue");
        System.out.println(line1);
        System.out.println("--------------------------------------------------------------");
        for (Order order : orders) {
            if (order.getStatus() == PREPARING) {
                String line = String.format("%1s%-10s%-15s%-15s%-10d%8.2f", " ", order.getOrderId(), order.getCustomerId(),
                        order.getCustomerName(), order.getQty(), order.getValue());
                System.out.println(line);
                System.out.println("--------------------------------------------------------------");
            }
        }
        L1: do {
            System.out.print("\nDo you want to go to home page (Y/N): ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y")) {
                clearConsole();
                homePage();
            } else if (option.equalsIgnoreCase("N")) {
                clearConsole();
                preparingOrder();
            } else {
                System.out.println("\tInvalid option..input again...");
                continue L1;
            }
        } while (true);
    }

    public static void cancelOrder() {
        Scanner input = new Scanner(System.in);
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("|\t\t\t\tCANCEL ORDER\t\t\t\t\t|");
        System.out.println("--------------------------------------------------------------------------------\n");
        System.out.println("\n--------------------------------------------------------------");
        String line1 = String.format("%-10s%-15s%-13s%-10s%12s", " OrderID", " CustomerID", " Name", "Quantity",
                "  OrderValue");
        System.out.println(line1);
        System.out.println("--------------------------------------------------------------");
        for (Order order : orders) {
            if (order.getStatus() == CANCEL) {
                String line = String.format("%1s%-10s%-15s%-15s%-10d%8.2f", " ", order.getOrderId(), order.getCustomerId(),
                        order.getCustomerName(), order.getQty(), order.getValue());
                System.out.println(line);
                System.out.println("--------------------------------------------------------------");
            }
        }
        L1: do {
            System.out.print("\nDo you want to go to home page (Y/N): ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y")) {
                clearConsole();
                homePage();
            } else if (option.equalsIgnoreCase("N")) {
                clearConsole();
                cancelOrder();
            } else {
                System.out.println("\tInvalid option..input again...");
                continue L1;
            }
        } while (true);
    }

    public static void updateOrderDetails() {
        Scanner input = new Scanner(System.in);
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("|\t\t\t\tUPDATE ORDER DETAILS\t\t\t\t|");
        System.out.println("--------------------------------------------------------------------------------\n");
        L1: do {
            System.out.print("Enter order Id - ");
            String orderIdInput = input.next();
            System.out.println();
            boolean found = false;
            for (Order order : orders) {
                if (orderIdInput.equals(order.getOrderId())) {
                    String status = "";
                    switch (order.getStatus()) {
                        case CANCEL: status = "Cancel"; break;
                        case PREPARING: status = "Preparing"; break;
                        case DELIVERED: status = "Delivered"; break;
                    }
                    if (order.getStatus() == CANCEL) {
                        System.out.println("This Order is already cancelled...You can not update this order...");
                    } else if (order.getStatus() == DELIVERED) {
                        System.out.println("This Order is already delivered...You can not update this order...");
                    } else {
                        System.out.println("OrderID    - " + order.getOrderId());
                        System.out.println("CustomerID - " + order.getCustomerId());
                        System.out.println("Name       - " + order.getCustomerName());
                        System.out.println("Quantity   - " + order.getQty());
                        System.out.printf("OrderValue - %.2f\n", order.getValue());
                        System.out.println("OrderStatus- " + status);

                        System.out.println("\nWhat do you want to update ? ");
                        System.out.println("\t(01) Quantity ");
                        System.out.println("\t(02) Status ");
                        System.out.print("\nEnter your option - ");
                        int option = input.nextInt();
                        switch (option) {
                            case 1:
                                clearConsole();
                                System.out.println("\nQuantity Update");
                                System.out.println("================\n");
                                System.out.println("OrderID    - " + order.getOrderId());
                                System.out.println("CustomerID - " + order.getCustomerId());
                                System.out.println("Name       - " + order.getCustomerName());
                                System.out.print("\nEnter your quantity update value - ");
                                int qty = input.nextInt();
                                order.setQty(qty);
                                order.setValue(qty * BURGERPRICE);
                                System.out.println("\n\tupdate order quantity success fully...");
                                System.out.println("\nnew order quantity - " + order.getQty());
                                System.out.printf("new order value - %.2f\n", order.getValue());
                                break;
                            case 2:
                                clearConsole();
                                System.out.println("\nStatus Update");
                                System.out.println("==============\n");
                                System.out.println("OrderID    - " + order.getOrderId());
                                System.out.println("CustomerID - " + order.getCustomerId());
                                System.out.println("Name       - " + order.getCustomerName());
                                System.out.println("\n\t(0)Cancel");
                                System.out.println("\t(1)Preparing");
                                System.out.println("\t(2)Delivered");
                                System.out.print("\nEnter new order status - ");
                                int s = input.nextInt();
                                order.setStatus(s);
                                switch (order.getStatus()) {
                                    case CANCEL: status = "Cancel"; break;
                                    case PREPARING: status = "Preparing"; break;
                                    case DELIVERED: status = "Delivered"; break;
                                }
                                System.out.println("\n\tUpdate order status successfully...");
                                System.out.println("\nnew order status - " + status);
                                break;
                        }
                    }
                    found = true;
                    break L1;
                }
            }
            if (!found) {
                L3: do {
                    System.out.print("\n\nInvalid Order ID. Do you want to enter again? (Y/N)>");
                    String option = input.next();
                    if (option.equalsIgnoreCase("Y")) {
                        System.out.println("\n");
                        continue L1;
                    } else if (option.equalsIgnoreCase("N")) {
                        clearConsole();
                        return;
                    } else {
                        System.out.println("\tInvalid option..input again...");
                        continue L3;
                    }
                } while (true);
            }
        } while (true);
        L3: do {
            System.out.println();
            System.out.print("Do you want to update another order details (Y/N): ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y")) {
                clearConsole();
                updateOrderDetails();
            } else if (option.equalsIgnoreCase("N")) {
                clearConsole();
                homePage();
            } else {
                System.out.println("\tInvalid option..input again...");
                continue L3;
            }
        } while (true);
    }

    public static void exit() {
        clearConsole();
        System.out.println("\n\t\tYou left the program...\n");
        System.exit(0);
    }

    public static void homePage() {
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("|\t\t\t\tiHungry Burger\t\t\t\t|");
        System.out.println("-------------------------------------------------------------------------\n");
        System.out.println("[1] Place Order\t\t\t[2] Search Best Customer");
        System.out.println("[3] Search Order\t\t[4] Search Customer");
        System.out.println("[5] View Orders\t\t\t[6] Update Order Details");
        System.out.println("[7] Exit");

        Scanner input = new Scanner(System.in);
        do {
            System.out.print("\nEnter an option to continue > ");
            char option = input.next().charAt(0);
            switch (option) {
                case '1': clearConsole(); placeOrder(); break;
                case '2': clearConsole(); searchBestCustomer(); break;
                case '3': clearConsole(); searchOrder(); break;
                case '4': clearConsole(); searchCustomer(); break;
                case '5': clearConsole(); viewOrders(); break;
                case '6': clearConsole(); updateOrderDetails(); break;
                case '7': exit(); break;
            }
        } while (true);
    }

    public static void main(String args[]) {
        homePage();
    }
}
