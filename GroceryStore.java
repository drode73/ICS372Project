package edu.ics372.grocerystore.interfaces;

/**
 * 
 * @author Brahma Dathan and Sarnath Ramnath
 * @Copyright (c) 2010
 
 * Redistribution and use with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - the use is for academic purpose only
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Neither the name of Brahma Dathan or Sarnath Ramnath
 *     may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * The authors do not make any claims regarding the correctness of the code in this module
 * and are not responsible for any loss or damage resulting from its use.  
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import edu.ics372.grocerystore.business.facade.GroceryStore;
import edu.ics372.grocerystore.business.facade.Request;
import edu.ics372.grocerystore.business.facade.Result;

public class UserInterface {
	private static UserInterface userInterface;
	private static GroceryStore groceryStore;

	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	private static final int EXIT = 0;
	private static final int ADD_MEMBER = 1;
	private static final int REMOVE_MEMBER = 2;
	private static final int GET_MEMBER_INFO = 3;
	private static final int ADD_PRODUCT = 4;
	private static final int CHECKOUT_MEMBER = 5;
	private static final int GET_PRODUCT_INFO = 6;
	private static final int PROCESS_SHIPMENT = 7;
	private static final int CHANGE_PRICE = 8;
	private static final int PRINT_TRANSACTIONS = 9;
	private static final int LIST_ALL_MEMBERS = 10;
	private static final int LIST_ALL_PRODUCTS = 11;
	private static final int LIST_OUTSTANDING_ORDERS = 12;
	private static final int SAVE = 13;
	private static final int RETRIEVE = 14;
	private static final int HELP = 15;

	/**
	 * Made private for singleton pattern. Conditionally looks for any saved data.
	 * Otherwise, it gets a singleton Library object.
	 */
	private UserInterface() {
		if (yesOrNo("Look for saved data and use it?")) {
			retrieve();
		} else {
			groceryStore = GroceryStore.instance();
		}
	}

	/**
	 * Supports the singleton pattern
	 * 
	 * @return the singleton object
	 */
	public static UserInterface instance() {
		if (userInterface == null) {
			userInterface = new UserInterface();
		}
		return userInterface;
	}

	/**
	 * Queries for a yes or no and returns true for yes and false for no
	 * 
	 * @param prompt The string to be prepended to the yes/no prompt
	 * @return true for yes and false for no
	 * 
	 */
	private boolean yesOrNo(String prompt) {
		String more = getToken(prompt + " (Y|y)[es] or anything else for no");
		if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
			return false;
		}
		return true;
	}

	/**
	 * Gets a token after prompting
	 * 
	 * @param prompt - whatever the user wants as prompt
	 * @return - the token from the keyboard
	 * 
	 */
	public String getToken(String prompt) {
		do {
			try {
				System.out.println(prompt);
				String line = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line, "\n\r\f");
				if (tokenizer.hasMoreTokens()) {
					return tokenizer.nextToken();
				}
			} catch (IOException ioe) {
				System.exit(0);
			}
		} while (true);
	}

	/**
	 * Gets a name after prompting
	 * 
	 * @param prompt - whatever the user wants as prompt
	 * @return - the token from the keyboard
	 * 
	 */
	public String getName(String prompt) {
		do {
			try {
				System.out.println(prompt);
				String line = reader.readLine().trim();
				return line;
			} catch (IOException ioe) {
				System.exit(0);
			}
		} while (true);

	}

	/**
	 * Converts the string to a number
	 * 
	 * @param prompt the string for prompting
	 * @return the integer corresponding to the string
	 * 
	 */
	public int getNumber(String prompt) {
		do {
			try {
				String item = getToken(prompt);
				Integer number = Integer.valueOf(item);
				return number.intValue();
			} catch (NumberFormatException nfe) {
				System.out.println("Please input a number ");
			}
		} while (true);
	}

	/**
	 * Converts the string to a Double
	 * 
	 * @param prompt the string for prompting
	 * @return the Double corresponding to the string
	 * 
	 */
	public double getDouble(String prompt) {
		do {
			try {
				String item = getToken(prompt);
				Double number = Double.valueOf(item);
				return number.doubleValue();
			} catch (NumberFormatException nfe) {
				System.out.println("Please input a double ");
			}
		} while (true);
	}

	/**
	 * Prompts for a date and gets a date object
	 * 
	 * @param prompt the prompt
	 * @return the data as a Calendar object
	 */
	public Calendar getDate(String prompt) {
		do {
			try {
				Calendar date = new GregorianCalendar();
				String item = getToken(prompt);
				DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
				date.setTime(dateFormat.parse(item));
				return date;
			} catch (Exception fe) {
				System.out.println("Please input a date as mm/dd/yy");
			}
		} while (true);
	}

	/**
	 * Prompts for a command from the keyboard
	 * 
	 * @return a valid command
	 * 
	 */
	public int getCommand() {
		do {
			try {
				int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
				if (value >= EXIT && value <= HELP) {
					return value;
				}
			} catch (NumberFormatException nfe) {
				System.out.println("Enter a number");
			}
		} while (true);
	}

	/**
	 * Displays the help screen
	 * 
	 */
	public void help() {
		System.out.println("Enter a number between 0 and 15 as explained below:");
		System.out.println(EXIT + " to Exit\n");
		System.out.println(ADD_MEMBER + " to add a member");
		System.out.println(REMOVE_MEMBER + " to remove a member");
		System.out.println(GET_MEMBER_INFO + " to retrieve member info");
		System.out.println(ADD_PRODUCT + " to add a new product");
		System.out.println(CHECKOUT_MEMBER + " to checkout a member's cart");
		System.out.println(GET_PRODUCT_INFO + " to retrieve product info");
		System.out.println(PROCESS_SHIPMENT + " to process a shipment of a product");
		System.out.println(CHANGE_PRICE + " to change the price of a product");
		System.out.println(PRINT_TRANSACTIONS + " to print transactions for a member between the two dates");
		System.out.println(LIST_ALL_MEMBERS + " to list all members");
		System.out.println(LIST_ALL_PRODUCTS + " to list all products");
		System.out.println(LIST_OUTSTANDING_ORDERS + " to print each order that has not been receieved yet");
		System.out.println(SAVE + " to save data");
		System.out.println(RETRIEVE + " to retrieve save data");
		System.out.println(HELP + " for help");
	}

	/**
	 * Orchestrates the whole process. Calls the appropriate method for the
	 * different functionalities.
	 * 
	 */
	public void process() {
		int command;
		help();

		while ((command = getCommand()) != EXIT) {
			switch (command) {
			case HELP:
				help();
				break;
			case ADD_MEMBER:
				addMember();
				break;
			case REMOVE_MEMBER:
				removeMember();
				break;
			case GET_MEMBER_INFO:
				getMemberInfo();
				break;
			case ADD_PRODUCT:
				addProduct();
				break;
			case CHECKOUT_MEMBER:
				checkoutMember();
				break;
			case GET_PRODUCT_INFO:
				getProductInfo();
				break;
			case PROCESS_SHIPMENT:
				processShipment();
				break;
			case CHANGE_PRICE:
				changePrice();
				break;
			case PRINT_TRANSACTIONS:
				printTransactions();
				break;
			case LIST_ALL_MEMBERS:
				listAllMembers();
				break;
			case LIST_ALL_PRODUCTS:
				listAllProducts();
				break;
			case LIST_OUTSTANDING_ORDERS:
				listOutstandingOrders();
				break;
			case SAVE:
				save();
				break;
			case RETRIEVE:
				retrieve();
				break;
			default:
				unkownCommand();
				break;
			}
		}
	}

	/**
	 * Method to be called for adding a member. Prompts the user for the appropriate
	 * values and uses the appropriate GroceryStore method for adding the member.
	 * 
	 */
	public void addMember() {
		Request.instance().setMemberName(getName("Enter a name"));
		Request.instance().setMemberAddress(getName("Enter an address"));
		Request.instance().setMemberPhoneNumber(getName("Enter a phone number"));
		Request.instance().setMemberFeePaid(Double.toString(getDouble("Enter fee paid")));

		Result result = groceryStore.addMember(Request.instance());

		if (result.getResultCode() != Result.OPERATION_COMPLETED) {
			System.out.println("Member could not be added");
		} else {
			System.out
					.println("Member: " + result.getMemberName() + " with ID: " + result.getMemberID() + " was added.");
		}
	}

	/**
	 * Method to be called for removing a member. Prompts the user for the member id
	 * and uses the appropriate GroceryStore method for removing the member.
	 * 
	 */
	public void removeMember() {
		Request.instance().setMemberID(getName("Enter ID of member to remove"));

		Result result = groceryStore.removeMember(Request.instance());

		switch (result.getResultCode()) {
		case Result.OPERATION_COMPLETED:
			System.out.println("Member with ID: " + result.getMemberID() + " successfully removed.");
			break;
		case Result.MEMBER_NOT_FOUND:
			System.out.println("Member " + Request.instance().getMemberID() + " not found.");
			break;
		case Result.OPERATION_FAILED:
			System.out.printf("Member: %s could not be removed\n", result.getMemberID());
			break;
		default:
			System.out.println("An error has occurred");
		}
	}

	/**
	 * Method to be called for getting information on members.
	 */
	public void getMemberInfo() {
		Request.instance().setMemberName((getName("Enter name of member to get information on")));

		Iterator<Result> result = groceryStore.getMemberInfo(Request.instance());

		while (result.hasNext()) {
			Result memberResult = result.next();
			System.out
					.println("Member: " + memberResult.getMemberName() + " Address: " + memberResult.getMemberAddress()
							+ " Fee Paid: " + memberResult.getMemberFeePaid() + " ID: " + memberResult.getMemberID());
		}
		System.out.println("\nEnd of list of searched members.\n");
	}

	/**
	 * Method to be used for adding a new product.
	 */
	public void addProduct() {
		Request.instance().setProductName(getName("Enter name of product"));
		Request.instance().setProductPrice(Double.toString(getDouble("Enter price of product e.x. 10.99")));
		Request.instance().setProductReorderLevel(Integer.toString(getNumber("Enter product reorder level")));

		Result result = groceryStore.addProduct(Request.instance());
		switch (result.getResultCode()) {
		case Result.PRODUCT_NAME_INVALID:
			System.out.println(
					"Product could not be added. Product name: " + result.getProductName() + " is already in use.");
			break;
		case Result.OPERATION_COMPLETED:
			System.out.println("Product successfully added. Name: " + result.getProductName() + " Price: "
					+ result.getProductPrice() + " Reorder level: " + result.getProductReorderLevel() + " ID: "
					+ result.getProductID());
			break;
		case Result.OPERATION_FAILED:
			System.out.println("Product could not be added.");
			break;
		}
	}

	/**
	 * Method for completing the checkout process.
	 */
	public void checkoutMember() {
		String memberID = getName("Enter ID of member to checkout.");
		// create new checkout
		Request.instance().setMemberID(memberID);
		Result result = groceryStore.createNewCheckout(Request.instance());
		if (result.getResultCode() != Result.OPERATION_COMPLETED) {
			System.out.println("New checkout could not be created");
			if (result.getResultCode() == Result.MEMBER_NOT_FOUND) {
				System.out.println("Member with ID: " + memberID + " was not found.");
			}
		} else {

			boolean continuing = true;

			while (continuing) {

				Request.instance().setMemberID(memberID);

				Request.instance().setProductID(Integer.toString(getNumber("Enter a product ID to add to checkout.")));
				Request.instance().setProductStock(
						Integer.toString(getNumber("Enter a quantity of product to add to checkout.")));
				result = groceryStore.addProductToCheckout(Request.instance());

				switch (result.getResultCode()) {
				case Result.PRODUCT_NOT_FOUND:
					System.out.println("Product was not found, could not be added to checkout.");
					break;
				case Result.PRODUCT_OUT_OF_STOCK:
					System.out.println(
							"Product does not have enough stock to add to checkout, was not added to checkout.");
					break;
				case Result.OPERATION_COMPLETED:
					System.out.println("Product was successfully added to cart.");
					break;
				}

				continuing = yesOrNo("Add another product?");
			}

			Request.instance().setMemberID(memberID);
			Iterator<Result> resultList = groceryStore.completeCheckout(Request.instance());
			Result productResult = new Result();
			List<String> reorderList = new LinkedList<String>();
			while (resultList.hasNext()) {
				productResult = resultList.next();
				double subTotal = Double.parseDouble(productResult.getProductPrice())
						* Integer.parseInt(productResult.getProductStock());
				System.out.println(productResult.getProductName() + "\t" + productResult.getProductStock() + "\t$"
						+ productResult.getProductPrice() + "\t$" + subTotal);
				if (productResult.getResultCode() == Result.PRODUCT_REORDERED) {
					reorderList.add(productResult.getProductName());
				}
			}
			System.out.println("Total\t\t\t$" + productResult.getTransactionTotalPrice());
			Iterator<String> reorderIterator = reorderList.iterator();
			while (reorderIterator.hasNext()) {
				System.out.println("Product: " + reorderIterator.next() + " has been reordered.");
			}

		}
	}

	/**
	 * Method for getting information of Products.
	 */
	public void getProductInfo() {
		Request.instance().setProductName(getName("Enter a product name."));
		Result result = groceryStore.getProductInfo(Request.instance());
		switch (result.getResultCode()) {
		case Result.PRODUCT_NOT_FOUND:
			System.out.println("Product: " + result.getProductName() + " was not found.");
			break;
		case Result.OPERATION_COMPLETED:
			System.out.println("Product: " + result.getProductName() + " ID: " + result.getProductID()
					+ " Curent stock: " + result.getProductStock());
			break;
		}
	}

	/**
	 * Method for the shipments of the Products
	 */
	public void processShipment() {
		do {
			Request.instance().setProductID(Integer.toString(getNumber("Enter product ID for shipment")));
			Request.instance().setProductStock(Integer.toString(getNumber("Enter quantity recieved.")));
			Result result = groceryStore.processShipment(Request.instance());
			switch (result.getResultCode()) {
			case Result.PRODUCT_NOT_FOUND:
				System.out.println("Product with ID: " + result.getProductID() + " was not found.");
				break;
			case Result.ORDER_NOT_FOUND:
				System.out.println("An order does not exist for Product: " + result.getProductID()
						+ ", so it will not be received.");
				break;
			case Result.INCORRECT_RECEIVED_QUANTITY:
				System.out.println("The recieved shipment for Product: " + result.getProductID() + " of quantity: "
						+ Request.instance().getProductStock() + " is not the correct amount ordered, which should be "
						+ (Integer.parseInt(result.getProductReorderLevel()) * 2) + ". The shipment is refused.");
				break;
			case Result.OPERATION_COMPLETED:
				System.out.println("Product ID: " + result.getProductID() + " Product name: " + result.getProductName()
						+ "recieved shipment. New product stock: " + result.getProductStock());
				break;
			}
		} while (yesOrNo("Process another shipment?"));
	}

	/**
	 * Method for changing the price of a Product from the user
	 */
	public void changePrice() {
		Request.instance().setProductID(Integer.toString(getNumber("Enter product ID")));
		Request.instance().setProductPrice(Double.toString(getDouble("Enter new price e.x. 10.99")));
		Result result = groceryStore.changePrice(Request.instance());
		switch (result.getResultCode()) {
		case Result.PRODUCT_NOT_FOUND:
			System.out.println("Product ID: " + result.getProductID() + " not found.");
			break;
		case Result.OPERATION_COMPLETED:
			System.out.printf("Product: %s\nNew Price: $%s\n", result.getProductName(), result.getProductPrice());
			break;
		}
	}

	/**
	 * Method for getting transactions for the member.
	 */
	public void printTransactions() {
		Request.instance().setMemberID(getName("Enter ID of member"));
		Request.instance().setStartDate(getDate("Enter start date"));
		Request.instance().setEndDate(getDate("Enter end date"));
		Iterator<Result> resultList = groceryStore.printTransactions(Request.instance());

		while (resultList.hasNext()) {
			Result transactionResult = resultList.next();
			switch (transactionResult.getResultCode()) {
			case Result.MEMBER_NOT_FOUND:
				System.out.println("Member: " + transactionResult.getMemberID() + " not found.");
				break;
			case Result.INVALID_DATES:
				System.out.println("Dates: " + transactionResult.getTransactionStartDate() + " and "
						+ transactionResult.getTransactionEndDate() + " are invalid dates.");
				break;
			case Result.OPERATION_COMPLETED:
				System.out.println("Visited on Date: " + transactionResult.getTransactionDate()
						+ " Checkout total price: $" + transactionResult.getTransactionTotalPrice());
				break;
			}
		}
	}

	/**
	 * Method for listing all members
	 */
	public void listAllMembers() {
		Iterator<Result> resultList = groceryStore.listAllMembers();
		while (resultList.hasNext()) {
			Result result = resultList.next();
			System.out.println("Name: " + result.getMemberName() + " ID: " + result.getMemberID() + " Address: "
					+ result.getMemberAddress());
		}
	}

	/**
	 * Method for listing all products
	 */
	public void listAllProducts() {
		Iterator<Result> resultList = groceryStore.listAllProducts();
		while (resultList.hasNext()) {
			Result result = resultList.next();
			System.out.println("Name: " + result.getProductName() + " ID: " + result.getProductID() + " Price: "
					+ result.getProductPrice() + " Reorder level: " + result.getProductReorderLevel());
		}
	}

	/**
	 * Method for listing all remaining orders on the products
	 */
	public void listOutstandingOrders() {
		Iterator<Result> resultList = groceryStore.listOutstandingOrders();
		while (resultList.hasNext()) {
			Result result = resultList.next();
			System.out.println("Name: " + result.getProductName() + " ID: " + result.getProductID()
					+ " Amount Ordered: " + result.getOrderQuantity());
		}
	}

	/**
	 * Method to be called for saving the file
	 */
	public void save() {
		if (groceryStore.save()) {
			System.out.println(" The file has been successfully saved in the file GroceryStoreData \n");
		} else {
			System.out.println(" There has been an error in saving \n");
		}
	}

	/**
	 * Method to be called for retrieving saved date
	 */
	public void retrieve() {
		try {
			if (groceryStore == null) {
				groceryStore = GroceryStore.retrieve();
				if (groceryStore != null) {
					System.out.println(" The file been successfully retrieved from the file GroceryStoreData \n");
				} else {
					System.out.println("File doesnt exist; creating new groceryStore");
					groceryStore = GroceryStore.instance();
				}
			}
		} catch (Exception cnfe) {
			cnfe.printStackTrace();
		}
	}

	/**
	 * Method for when an unknown command is entered by the user.
	 */
	public void unkownCommand() {
		System.out.println("Unknown Command");
		help();
	}

	public static void main(String[] args) {
		UserInterface.instance().process();
	}
}
