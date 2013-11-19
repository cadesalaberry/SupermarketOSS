package test;

import java.lang.reflect.Field;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.text.JTextComponent;

import org.easymock.EasyMock;

import Counter.MarketProxy;
import CounterUI.CounterFrame;
import static org.junit.Assert.*;

import org.junit.Test;

public class CounterUITest {
	private static boolean visibility = false;
	
	/**
	 * TC1
	 */
	@Test
	public void normalAcquisition() {
		MarketProxy marketProxy = EasyMock.createNiceMock(MarketProxy.class);
		CounterFrame counter = new CounterFrame(marketProxy);
		counter.setVisible(visibility);
		
		try {
			EasyMock.expect(marketProxy.connect(1, "")).andReturn("employee");
			EasyMock.expect(marketProxy.getCustomer(999)).andReturn("badassName");
			EasyMock.expect(marketProxy.getPrice(666)).andReturn(69.0);
			EasyMock.expect(marketProxy.getName(666)).andReturn("deviledEggs");
			
			EasyMock.replay(marketProxy);
			
			click(counter, "ConnectButton");
			
			String message = getTextFromField(counter, "messageLine");
			
			//checks successful login
			assertEquals(message, "Connection successfull, welcome employee");
			
			//employee enters customer code
			setTextFromField(counter, "customerCode", "999");
			click(counter, "btCustomer");
			
			String customerName = getTextFromField(counter, "customerName");
			
			//checks customer's name
			assertEquals(customerName, "badassName");
			
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
		MarketProxy marketProxy = EasyMock.createNiceMock(MarketProxy.class);
		CounterFrame counter = new CounterFrame(marketProxy);
		counter.setVisible(visibility);
		
		try {
			EasyMock.expect(marketProxy.connect(1, "")).andReturn("employee");
			EasyMock.expect(marketProxy.getCustomer(999)).andReturn("badassName");
			EasyMock.expect(marketProxy.getPrice(666)).andReturn(69.0);
			EasyMock.expect(marketProxy.getName(666)).andReturn("deviledEggs");
			
			EasyMock.replay(marketProxy);
			
			click(counter, "ConnectButton");
			
			String message = getTextFromField(counter, "messageLine");
			
			//checks successful login
			assertEquals(message, "Connection successfull, welcome employee");
			
			//no method exists to create a new customer
			fail("Error: Could not create new customer");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * TC3
	 */
	@Test
	public void anonymousCustomer() {
		MarketProxy marketProxy = EasyMock.createNiceMock(MarketProxy.class);
		CounterFrame counter = new CounterFrame(marketProxy);
		counter.setVisible(visibility);
		
		try {
			EasyMock.expect(marketProxy.connect(1, "")).andReturn("employee");
			EasyMock.expect(marketProxy.getCustomer(999)).andReturn("badassName");
			EasyMock.expect(marketProxy.getPrice(666)).andReturn(69.0);
			EasyMock.expect(marketProxy.getName(666)).andReturn("deviledEggs");
			
			EasyMock.replay(marketProxy);
			
			click(counter, "ConnectButton");
			
			String message = getTextFromField(counter, "messageLine");
			
			//checks successful login
			assertEquals(message, "Connection successfull, welcome employee");
			
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
		MarketProxy marketProxy = EasyMock.createNiceMock(MarketProxy.class);
		CounterFrame counter = new CounterFrame(marketProxy);
		counter.setVisible(visibility);
		
		try {
			EasyMock.expect(marketProxy.connect(1, "")).andReturn("employee");
			EasyMock.expect(marketProxy.getCustomer(999)).andReturn("badassName");
			EasyMock.expect(marketProxy.getPrice(666)).andReturn(69.0);
			EasyMock.expect(marketProxy.getName(666)).andReturn("deviledEggs");
			
			EasyMock.replay(marketProxy);
			
			click(counter, "ConnectButton");
			
			String message = getTextFromField(counter, "messageLine");
			
			//checks successful login
			assertEquals(message, "Connection successfull, welcome employee");
			
			//employee enters customer code
			setTextFromField(counter, "customerCode", "999");
			click(counter, "btCustomer");
			
			String customerName = getTextFromField(counter, "customerName");
			
			//checks customer's name
			assertEquals(customerName, "badassName");
			
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
		MarketProxy marketProxy = EasyMock.createNiceMock(MarketProxy.class);
		CounterFrame counter = new CounterFrame(marketProxy);
		counter.setVisible(visibility);
		
		try {
			EasyMock.expect(marketProxy.connect(1, "")).andReturn("employee");
			EasyMock.expect(marketProxy.getCustomer(999)).andReturn("badassName");
			EasyMock.expect(marketProxy.getPrice(666)).andReturn(69.0);
			EasyMock.expect(marketProxy.getName(666)).andReturn("deviledEggs");
			EasyMock.expect(marketProxy.getPrice(42)).andReturn(1.0);
			EasyMock.expect(marketProxy.getName(42)).andReturn("theAnswer");
			
			EasyMock.replay(marketProxy);
			
			click(counter, "ConnectButton");
			
			String message = getTextFromField(counter, "messageLine");
			
			//checks successful login
			assertEquals(message, "Connection successfull, welcome employee");
			
			//employee enters customer code
			setTextFromField(counter, "customerCode", "999");
			click(counter, "btCustomer");
			
			String customerName = getTextFromField(counter, "customerName");
			
			//checks customer's name
			assertEquals(customerName, "badassName");
			
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
			assertEquals(total,"70.0");
			
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
		MarketProxy marketProxy = EasyMock.createNiceMock(MarketProxy.class);
		CounterFrame counter = new CounterFrame(marketProxy);
		counter.setVisible(visibility);
		
		try {
			EasyMock.expect(marketProxy.connect(1, "")).andReturn("employee");
			EasyMock.replay(marketProxy);
			
			click(counter, "ConnectButton");
			
			String message = getTextFromField(counter, "messageLine");
			
			assertEquals(message, "Connection successfull, welcome employee");
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
		MarketProxy marketProxy = EasyMock.createNiceMock(MarketProxy.class);
		CounterFrame counter = new CounterFrame(marketProxy);
		counter.setVisible(visibility);
		
		try {
			EasyMock.expect(marketProxy.connect(12, "")).andReturn("employee");
			EasyMock.replay(marketProxy);
			
			click(counter, "ConnectButton");
			
			String message = getTextFromField(counter, "messageLine");
			
			assertEquals(message, "Wrong code or password.");
			
			setTextFromField(counter, "employeeName", "12");
			
			click(counter, "ConnectButton");
			
			message = getTextFromField(counter, "messageLine");
			
			assertEquals(message, "Connection successfull, welcome employee");
			
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
		MarketProxy marketProxy = EasyMock.createNiceMock(MarketProxy.class);
		CounterFrame counter = new CounterFrame(marketProxy);
		counter.setVisible(visibility);
		
		try {
			EasyMock.expect(marketProxy.connect(1, "")).andReturn("employee");
			EasyMock.replay(marketProxy);
			
			//connect
			click(counter, "ConnectButton");
			
			String message = getTextFromField(counter, "messageLine");
			
			assertEquals(message, "Connection successfull, welcome employee");
			
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
	
}