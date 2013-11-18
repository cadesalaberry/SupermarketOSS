package test;

import CounterUI.CounterFrame;

public class TestCounterFrame {

	public static void main(String[] args) {
		CounterFrame counter = new CounterFrame(new MarketProxyStub());
		counter.setVisible(true);
	}

}