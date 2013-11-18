package test;

import java.lang.reflect.Field;

import javax.swing.JButton;
import javax.swing.JToggleButton;

import org.easymock.EasyMock;

import Counter.MarketProxy;
import CounterUI.CounterFrame;

public class TestCounterFrame {

	public static void main(String[] args) {
		MarketProxy marketProxy = EasyMock.createNiceMock(MarketProxy.class);
		CounterFrame counter = new CounterFrame(marketProxy);
		counter.setVisible(true);
		
		try {
			EasyMock.expect(marketProxy.connect(1, "")).andReturn("employee");
			EasyMock.replay(marketProxy);
			
			Field jButtonField = CounterFrame.class.getDeclaredField("bt5");
			jButtonField.setAccessible(true);
			JButton bt5 = (JButton)jButtonField.get(counter);
			bt5.doClick();
			//bt5.doClick();
			
			Field connectButtonField = CounterFrame.class.getDeclaredField("ConnectButton");
			connectButtonField.setAccessible(true);
			JToggleButton connectButton = (JToggleButton)connectButtonField.get(counter);
			connectButton.doClick();
		}
		catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
//	public static void tc1() {
//		bt1.doClick();
//		ConnectButton
//	}
}