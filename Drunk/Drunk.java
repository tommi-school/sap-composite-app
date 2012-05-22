import java.util.*;
import java.io.*;
import java.sql.*;
import org.apache.axiom.om.*;

public class Drunk {
	String dbURL="jdbc:mysql://localhost:3306/ebs";
    String dbDriver = "com.mysql.jdbc.Driver";
	private Connection dbCon;
	
	// connects to the database using root. change your database id/password here if necessary
    private boolean connect() throws ClassNotFoundException, SQLException {
        Class.forName(dbDriver);

        // login credentials to your MySQL server
        dbCon = DriverManager.getConnection(dbURL, "root", "");
        return true;
    }

	// closes the DB connection
    private void close() throws SQLException {
        dbCon.close();
    }

	//method to test
	public String helloWorld() {
		System.out.println("Hello world method was called");
		return "Hello World";
	}
	
	//method to test
	public String helloWorld2() {
		System.out.println("Hello world2 method was called");
		return "Hello World2";
	}
	
	//method to test
	public String blueMoon(String input) {
		System.out.println("blueMoon method is called, returning value " + input + " . This method was called at: " + new java.util.Date());
		return input;
	}
	
	private ItemType getItemTypeByProductId(ItemType[] itemTypes, String productId) {
		for (ItemType it : itemTypes) {
			if (it.getProduct().equals(productId)) {
				return it;
			}
		}
		
		return null;
	}
	
	private ItemType[] resizeItemTypeArray(ItemType[] itemTypes, int actualSize) {
		ItemType[] newArray = new ItemType[actualSize];
		
		for (int i=0; i<actualSize; i++) {
			newArray[i] = itemTypes[i];
		}
		
		return newArray;
	}
	
	private void printItemTypeArray(ItemType[] itemTypes) {
		for (ItemType it : itemTypes) {
			System.out.println(it);
		}
		
		if (itemTypes == null) {
			System.out.println("INFO: The ItemType array is null.");	
		}
	}
	
	public CheckInventoryResponse checkInventory(ItemType[] custOrder, ItemType[] sapInventory) {
		System.out.println("----------checkInventory was called----------");
		System.out.println("Printing out custOrder array:");
		printItemTypeArray(custOrder);
		System.out.println("Printing out sapInventory array:");
		printItemTypeArray(sapInventory);

		CheckInventoryResponse response = null;
		ItemType[] responseItemTypes = new ItemType[20];
		int responseItemTypesCount = 0;
		boolean toPurchase = false;
		
		for (int i=0; i<custOrder.length; i++) { 
			ItemType co = custOrder[i];
			
			//query to get qty AWS
			try {
				connect();

				Statement s = dbCon.createStatement();
		        ResultSet r = s.executeQuery("select sum(sot.qty) from sales_orders so, sales_order_items sot where so.id=sot.sales_order_id and so.is_open=true and item_id=" + co.getId());

				if (r == null) {
					System.out.println("no records in the sales_order table");
				}
				
				int orderedQty = -1;
		        while (r.next()) {
		            orderedQty += r.getInt(1);
		        }
				
				ItemType sapInventoryItemType = getItemTypeByProductId(sapInventory, co.getProduct());
				
				int availableQty = 0;
				if (sapInventoryItemType == null) {
					availableQty = Integer.parseInt(sapInventoryItemType.getQty()) - orderedQty;
				}
				 
				if (Integer.parseInt(co.getQty()) > availableQty || sapInventoryItemType == null) {
					int poQty = Integer.parseInt(co.getQty()) - availableQty;
					
					String poIdWithZeros = appendZeros(18 , Integer.parseInt(co.getProduct()));
					System.out.println("poIdWithZeros: " + poIdWithZeros);
					 
					ItemType poItemType = new ItemType(co.getId(), poIdWithZeros, String.valueOf(poQty));
					
					// if (responseItemTypes == null) {
					// 						System.out.println("poItemType is null");
					// 					}

					responseItemTypes[responseItemTypesCount] = poItemType;
					responseItemTypesCount++;
				}	
				
				System.out.println("Product ID: " + co.getProduct() + " Available Qty: " + availableQty);

				close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (responseItemTypesCount > 0) {
				toPurchase = true;
			}
		
		}
		
		responseItemTypes = resizeItemTypeArray(responseItemTypes, responseItemTypesCount);
		response = new CheckInventoryResponse(responseItemTypes, toPurchase);
		
		System.out.println("---Printing response---");
		printItemTypeArray(responseItemTypes);
		System.out.println("toPurchase: " + toPurchase);
		
		System.out.println();
		System.out.println();
		System.out.println();
		
		return response;
	}
	
	public String addCustomerOrder(String salesOrderId, ItemType[] custOrder) {
		System.out.println("----------addCustomerOrder was called----------");
		System.out.println("Printing out custOrder array:");
		printItemTypeArray(custOrder);
		System.out.println("Sales Order ID: " + salesOrderId);
				
		try {
			connect();
			
			Statement s = dbCon.createStatement();
			
			//add sales order
			s.executeUpdate("insert into sales_orders values ("+ salesOrderId +", true)");
			
			//add items	
			for (ItemType it : custOrder) {
				s.executeUpdate("insert into sales_order_items values("+ salesOrderId	 +", "+ it.getProduct() +", "+ it.getQty() +")");
			}
			
			close();
		} catch (Exception e) {
			System.out.println("-----Caught an error in addCustomerOrder-----");
			e.printStackTrace();
		}
		
		System.out.println();
		System.out.println();
		System.out.println();
		
		return "A very nice message.";
	}
	
	public String updateCustomerOrder(String salesOrderId, ItemType[] custOrder) {
		System.out.println("----------updateCustomerOrder was called----------");
		System.out.println("Printing out custOrder array:");
		printItemTypeArray(custOrder);
		System.out.println("Sales Order ID: " + salesOrderId);
		
		try {
			connect();
			
			Statement s = dbCon.createStatement();
			
			//update row(s) in sales_order_item table
			for (ItemType it : custOrder) {
				s.executeUpdate("update sales_order_items set qty="+ it.getQty() +" where item_id=" + it.getProduct() + " and sales_order_id=" + salesOrderId);
			}
			
			//closing sales order status
			int rowsAffected = s.executeUpdate("update sales_orders set is_open=false where id=" + salesOrderId);
			if (rowsAffected != 1) {
				System.out.println("Something is wrong, more than 1 rows affected when updating sales order status");
			}
			
			close();
		} catch (Exception e) {
			System.out.println("-----Caught an error in updateCustomerOrder-----");
			e.printStackTrace();
		}
	
		System.out.println();
		System.out.println();
		System.out.println();
		
		return "A very nice message.";
	}
	
	public String addDeliveryRecord(String deliveryDate, String weight, String customerName, String address) {
			System.out.println("----------addDeliveryRecord was called----------");
			System.out.println("Delivery record - Delivery Date: " + deliveryDate + "/Weight: " + weight + "/Customer Name: " + customerName + "/Address: " + address);

			try {
				connect();

				PreparedStatement s = dbCon.prepareStatement("insert into delivery_records(delivery_date, weight, customer_name, address) values ('?', '?', '?', '?')");
				s.setString(1, deliveryDate);
				s.setString(2, weight);
				s.setString(3, customerName);
				s.setString(4, address);
				
				s.executeUpdate();
				
				close();
			} catch (Exception e) {
				System.out.println("-----Caught an error in addCustomerOrder-----");
				e.printStackTrace();
			}

			System.out.println();
			System.out.println();
			System.out.println();
			
			appendToFile(deliveryDate + ";" + weight + ";" + customerName + ";" + address);
			
			return "A very nice message.";
	}
	
	private void appendToFile(String input) {
		try {
			// Create file 
			FileWriter fstream = new FileWriter("/home/ebs/delivery.txt", true);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(input);
			
			//Close the output stream
			out.close();
		} catch (Exception e) {//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	private String appendZeros(int digits, int num) {
		String format = String.format("%%0%dd", digits);
		String result = String.format(format, num);
		return result;
	}
}
