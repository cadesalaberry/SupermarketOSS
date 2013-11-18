package test;

import java.lang.reflect.Field;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import org.easymock.EasyMock;

import Counter.MarketProxy;
import CounterUI.CounterFrame;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestCounterFrame {
	private boolean visibility = true;

	
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
			
			String message = clickConnect(counter);
			
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
			
			String message = clickConnect(counter);
			
			assertEquals(message, "Wrong code or password.");
			
			Field employeeCodeField = CounterFrame.class.getDeclaredField("employeeName");
			employeeCodeField.setAccessible(true);
			JTextField employeeCode = (JTextField)employeeCodeField.get(counter);
			
			employeeCode.setText("12");
			
			message = clickConnect(counter);
			
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
			
			String message = clickConnect(counter);
			
			assertEquals(message, "Connection successfull, welcome employee");
			
			message = clickConnect(counter);
			
			assertEquals(message, "Disconnection successfull");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String clickConnect(CounterFrame counter) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field connectButtonField = CounterFrame.class.getDeclaredField("ConnectButton");
		connectButtonField.setAccessible(true);
		JToggleButton connectButton = (JToggleButton)connectButtonField.get(counter);
		connectButton.doClick();
		
		Field messageField = CounterFrame.class.getDeclaredField("messageLine");
		messageField.setAccessible(true);
		JTextField messageLine = (JTextField)messageField.get(counter);
		
		return messageLine.getText();
	}
}