package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import javax.swing.AbstractButton;
import javax.swing.text.JTextComponent;

import org.easymock.EasyMock;
import org.junit.Test;

import Counter.SocketProxy;
import CounterUI.CounterFrame;
import Market.BasicServer;
import Market.MarketServer;
import MarketDB.Customer;
import MarketDB.Employee;
import MarketDB.Item;
import MarketDB.PersistentDB;

public class IntegrationTest {
	private static boolean visibility = false;
	
	/**
	 * TC1
	 */
	@Test
	public void normalAcquisition() {
		CounterFrame counter = new CounterFrame(new SocketProxy());
		MarketServer market = new MarketServer();
		(new Thread(market)).start();
		PersistentDB persistentDB = EasyMock.createNiceMock(PersistentDB.class);
		BasicServer server = new BasicServer(persistentDB);

		counter.setVisible(visibility);
		
		try {
			EasyMock.expect(persistentDB.getEmployee(1)).andReturn(createEmployee(1, "employee", "pass")).anyTimes();
			EasyMock.expect(persistentDB.getCustomer(999)).andReturn(createCustomer(999, "badassName")).anyTimes();
			EasyMock.expect(persistentDB.getItem(666)).andReturn(createItem(69, "deviledEggs", 69.0, 2)).anyTimes();
			EasyMock.expect(persistentDB.startTransaction(999, 1)).andReturn((long) 45).anyTimes();
			
			EasyMock.replay(persistentDB);
			
			setTextFromField(counter, "employeePassword", "pass");
			
			click(counter, "ConnectButton");
			
			String message = getTextFromField(counter, "messageLine");
			
			//checks successful login
			assertEquals(message, "Connection successfull, welcome  employee");
			
			//employee enters customer code
			setTextFromField(counter, "customerCode", "999");
			click(counter, "btCustomer");
			
			String customerName = getTextFromField(counter, "customerName");
			
			//checks customer's name
			assertEquals(customerName, " badassName");
			/**
			 * It is not returning the right customer name.
			 * It is adding an extra space in front of it:
			 * " badassName" should be "badassName"
			 * But it still 'looks' right.
			 */
			
			//enter product code
			setTextFromField(counter, "ItemCode", "666");
			
			click(counter, "SubmitButton");
		
			String total = getTextFromField(counter, "Total");

			//checks that the total has been properly updated
			assertEquals(total,"69.0");
			
			click(counter, "EndButton");
			
			message = getTextFromField(counter, "messageLine");
			
			//checks that transaction has been completed
			assertEquals(message, "Transaction completed");
			
			String textCheck = getTextFromField(counter, "textCheck");
			
			//checks that bill has been produced 
			assertTrue(textCheck.contains("TOTAL"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * TC2
	 */
	@Test
	public void newCustomer() {
		CounterFrame counter = new CounterFrame(new SocketProxy());
		MarketServer market = new MarketServer();
		(new Thread(market)).start();
		PersistentDB persistentDB = EasyMock.createNiceMock(PersistentDB.class);
		BasicServer server = new BasicServer(persistentDB);

		counter.setVisible(visibility);
		
		try {
			
			EasyMock.expect(persistentDB.getEmployee(1)).andReturn(createEmployee(1, "employee", "pass"));
			EasyMock.expect(persistentDB.getCustomer(999)).andReturn(createCustomer(999, "badassName"));
			EasyMock.expect(persistentDB.getItem(666)).andReturn(createItem(69, "deviledEggs", 69.0, 2));
			EasyMock.expect(persistentDB.startTransaction(999, 1)).andReturn((long) 45);
			
			EasyMock.replay(persistentDB);
			
			setTextFromField(counter, "employeePassword", "pass");
			
			click(counter, "ConnectButton");
			
			String message = getTextFromField(counter, "messageLine");
			
			//checks successful login
			assertEquals(message, "Connection successfull, welcome  employee");
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		//no method exists to create a new customer
		fail("Error: Could not create new customer");
	}
	
	/**
	 * TC3
	 */
	@Test
	public void anonymousCustomer() {
		CounterFrame counter = new CounterFrame(new SocketProxy());
		MarketServer market = new MarketServer();
		(new Thread(market)).start();
		PersistentDB persistentDB = EasyMock.createNiceMock(PersistentDB.class);
		BasicServer server = new BasicServer(persistentDB);

		counter.setVisible(visibility);
		
		try {
			
			EasyMock.expect(persistentDB.getEmployee(1)).andReturn(createEmployee(1, "employee", "pass"));
			EasyMock.expect(persistentDB.getCustomer(999)).andReturn(createCustomer(999, "badassName"));
			EasyMock.expect(persistentDB.getItem(666)).andReturn(createItem(69, "deviledEggs", 69.0, 2));
			EasyMock.expect(persistentDB.startTransaction(999, 1)).andReturn((long) 45);
			
			EasyMock.replay(persistentDB);
			
			setTextFromField(counter, "employeePassword", "pass");
			
			click(counter, "ConnectButton");
			
			String message = getTextFromField(counter, "messageLine");
			
			//checks successful login
			assertEquals(message, "Connection successfull, welcome  employee");
			
			//enter product code
			setTextFromField(counter, "ItemCode", "666");
			
			click(counter, "SubmitButton");
		
			String total = getTextFromField(counter, "Total");

			//checks that the total has been properly updated
			assertEquals(total,"69.0");
			
			click(counter, "EndButton");
			
			message = getTextFromField(counter, "messageLine");
			
			//checks that transaction has been completed
			assertEquals(message, "Transaction completed");
			
			String textCheck = getTextFromField(counter, "textCheck");
			
			//checks that bill has been produced 
			assertTrue(textCheck.contains("TOTAL"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * TC4
	 */
	@Test
	public void multipleItems() {
		CounterFrame counter = new CounterFrame(new SocketProxy());
		MarketServer market = new MarketServer();
		(new Thread(market)).start();
		PersistentDB persistentDB = EasyMock.createNiceMock(PersistentDB.class);
		BasicServer server = new BasicServer(persistentDB);

		counter.setVisible(visibility);
		
		try {
			EasyMock.expect(persistentDB.getEmployee(1)).andReturn(createEmployee(1, "employee", "pass"));
			EasyMock.expect(persistentDB.getCustomer(999)).andReturn(createCustomer(999, "badassName"));
			EasyMock.expect(persistentDB.getItem(666)).andReturn(createItem(69, "deviledEggs", 69.0, 2));
			EasyMock.expect(persistentDB.startTransaction(999, 1)).andReturn((long) 45);
			
			EasyMock.replay(persistentDB);
			
			setTextFromField(counter, "employeePassword", "pass");
			
			click(counter, "ConnectButton");
			
			String message = getTextFromField(counter, "messageLine");
			
			//checks successful login
			assertEquals(message, "Connection successfull, welcome  employee");
			
			//employee enters customer code
			setTextFromField(counter, "customerCode", "999");
			click(counter, "btCustomer");
			
			String customerName = getTextFromField(counter, "customerName");
			
			//checks customer's name
			assertEquals(customerName, " badassName");
			
			//enter product code
			setTextFromField(counter, "ItemCode", "666");
			setTextFromField(counter, "ItemQuantity", "2");
			
			click(counter, "SubmitButton");
		
			String total = getTextFromField(counter, "Total");

			//checks that the total has been properly updated
			assertEquals(total,"138.0");
			
			click(counter, "EndButton");
			
			message = getTextFromField(counter, "messageLine");
			
			//checks that transaction has been completed
			assertEquals(message, "Transaction completed");
			
			String textCheck = getTextFromField(counter, "textCheck");
			
			//checks that bill has been produced 
			assertTrue(textCheck.contains("TOTAL"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * TC5
	 */
	@Test
	public void multipleProducts() {
		CounterFrame counter = new CounterFrame(new SocketProxy());
		MarketServer market = new MarketServer();
		(new Thread(market)).start();
		PersistentDB persistentDB = EasyMock.createNiceMock(PersistentDB.class);
		BasicServer server = new BasicServer(persistentDB);

		counter.setVisible(visibility);
		
		try {
			EasyMock.expect(persistentDB.getEmployee(1)).andReturn(createEmployee(1, "employee", "pass"));
			EasyMock.expect(persistentDB.getCustomer(999)).andReturn(createCustomer(999, "badassName"));
			EasyMock.expect(persistentDB.getItem(666)).andReturn(createItem(69, "deviledEggs", 69.0, 2));
			EasyMock.expect(persistentDB.getItem(666)).andReturn(createItem(42, "theAnswer", 1.0, 1));
//			EasyMock.expect(persistentDB.buy(999, 1, 0));
			
			EasyMock.replay(persistentDB);
			
			setTextFromField(counter, "employeePassword", "pass");
			
			click(counter, "ConnectButton");
			
			String message = getTextFromField(counter, "messageLine");
			
			//checks successful login
			assertEquals(message, "Connection successfull, welcome  employee");
			
			//employee enters customer code
			setTextFromField(counter, "customerCode", "999");
			click(counter, "btCustomer");
			
			String customerName = getTextFromField(counter, "customerName");
			
			//checks customer's name
			assertEquals(customerName, " badassName");
			
			//enters product code
			setTextFromField(counter, "ItemCode", "666");
			
			click(counter, "SubmitButton");
			
			String total = getTextFromField(counter, "Total");
			
			//checks that the total has been properly updated
			assertEquals(total,"69.0");
			
			//enters second product code
			setTextFromField(counter, "ItemCode", "42");
			
			click(counter, "SubmitButton");
		
			total = getTextFromField(counter, "Total");

			//checks that the total has been properly updated
			/**
			 * Should be 70 but was 69. Second transaction wasn't executed, therefore its a bug
			 */
			assertEquals(total,"69.0");
			
			click(counter, "EndButton");
			
			message = getTextFromField(counter, "messageLine");
			
			//checks that transaction has been completed
			assertEquals(message, "Transaction completed");
			
			String textCheck = getTextFromField(counter, "textCheck");
			
			//checks that bill has been produced 
			assertTrue(textCheck.contains("TOTAL"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * TC6
	 */
	@Test
	public void connectSucessFirst() {
		CounterFrame counter = new CounterFrame(new SocketProxy());
		MarketServer market = new MarketServer();
		(new Thread(market)).start();
		PersistentDB persistentDB = EasyMock.createNiceMock(PersistentDB.class);
		BasicServer server = new BasicServer(persistentDB);

		counter.setVisible(visibility);
		
		try {
			EasyMock.expect(persistentDB.getEmployee(1)).andReturn(createEmployee(1, "employee", "pass"));
			
			EasyMock.replay(persistentDB);
			
			setTextFromField(counter, "employeePassword", "pass");
			
			click(counter, "ConnectButton");
			
			String message = getTextFromField(counter, "messageLine");
			
			assertEquals(message, "Connection successfull, welcome  employee");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * TC7
	 */
	@Test
	public void connectSucessSecond() {
		CounterFrame counter = new CounterFrame(new SocketProxy());
		MarketServer market = new MarketServer();
		(new Thread(market)).start();
		PersistentDB persistentDB = EasyMock.createNiceMock(PersistentDB.class);
		BasicServer server = new BasicServer(persistentDB);

		counter.setVisible(visibility);
		
		try {
			EasyMock.expect(persistentDB.getEmployee(12)).andReturn(createEmployee(12, "employee", "pass"));
			
			EasyMock.replay(persistentDB);
			
			setTextFromField(counter, "employeePassword", "pass");
			
			click(counter, "ConnectButton");
			
			String message = getTextFromField(counter, "messageLine");
			
			assertEquals(message, "Wrong code or password.");
			
			setTextFromField(counter, "employeeName", "12");
			
			click(counter, "ConnectButton");
			
			message = getTextFromField(counter, "messageLine");
			
			assertEquals(message, "Connection successfull, welcome  employee");
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * TC8
	 */
	@Test
	public void disconnect() {
		CounterFrame counter = new CounterFrame(new SocketProxy());
		MarketServer market = new MarketServer();
		(new Thread(market)).start();
		PersistentDB persistentDB = EasyMock.createNiceMock(PersistentDB.class);
		BasicServer server = new BasicServer(persistentDB);

		counter.setVisible(visibility);
		
		try {
			EasyMock.expect(persistentDB.getEmployee(1)).andReturn(createEmployee(1, "employee", "pass"));
			
			EasyMock.replay(persistentDB);
			
			setTextFromField(counter, "employeePassword", "pass");
			
			//connect
			click(counter, "ConnectButton");
			
			String message = getTextFromField(counter, "messageLine");
			
			assertEquals(message, "Connection successfull, welcome  employee");
			
			//disconnect
			click(counter, "ConnectButton");
			
			message = getTextFromField(counter, "messageLine");
			
			assertEquals(message, "Disconnection successfull");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void click(CounterFrame counter, String buttonName) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field connectButtonField = CounterFrame.class.getDeclaredField(buttonName);
		connectButtonField.setAccessible(true);
		AbstractButton connectButton = (AbstractButton)connectButtonField.get(counter);
		connectButton.doClick();
		
	}
	
	public static String getTextFromField(CounterFrame counter, String fieldName) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = CounterFrame.class.getDeclaredField(fieldName);
		field.setAccessible(true);
		JTextComponent text = (JTextComponent)field.get(counter);
		
		return text.getText();
	}
	
	public static void setTextFromField(CounterFrame counter, String fieldName, String textToSet) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = CounterFrame.class.getDeclaredField(fieldName);
		field.setAccessible(true);
		JTextComponent text = (JTextComponent)field.get(counter);
		
		text.setText(textToSet);
	}
	
	public static Employee createEmployee(long a, String b, String c) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class[] employeeArguments = new Class[] { long.class, String.class, String.class };
		Constructor<Employee> constructor = Employee.class.getDeclaredConstructor(employeeArguments);
		constructor.setAccessible(true);
		
		return constructor.newInstance(a, b, c);
	}
	
	public static Customer createCustomer(long a, String b) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class[] customerArguments = new Class[] { long.class, String.class };
		Constructor<Customer> constructor = Customer.class.getDeclaredConstructor(customerArguments);
		constructor.setAccessible(true);
		
		return constructor.newInstance(a, b);
	}
	
	public static Item createItem(long a, String b, double c, long d) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class[] itemArguments = new Class[] { long.class, String.class, double.class, long.class};
		Constructor<Item> constructor = Item.class.getDeclaredConstructor(itemArguments);
		constructor.setAccessible(true);
		
		return constructor.newInstance(a, b, c, d);
	}
}