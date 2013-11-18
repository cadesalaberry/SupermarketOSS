package test;

import Counter.MarketProxy;

public class MarketProxyStub implements MarketProxy {

	@Override
	public String connect(long employee, String password) throws Exception {
		// TODO Auto-generated method stub
		return "connect";
	}

	@Override
	public String getName(long item) throws Exception {
		// TODO Auto-generated method stub
		return "getName";
	}

	@Override
	public double getPrice(long item) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCustomer(long code) throws Exception {
		// TODO Auto-generated method stub
		return "getCustomer";
	}

	@Override
	public void startTransaction(long customer, long employee) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void buy(long item, long quantity) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void endTransaction() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void abortTransaction() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub

	}

}