package test;

import org.easymock.EasyMock;

import Counter.MarketProxy;
import CounterUI.CounterFrame;

public class TestCounterFrame {

	public static void main(String[] args) {
		MarketProxy marketProxy = EasyMock.createNiceMock(MarketProxy.class);
		CounterFrame counter = new CounterFrame(marketProxy);
		counter.setVisible(true);
	}

}