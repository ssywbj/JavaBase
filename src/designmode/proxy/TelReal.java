package designmode.proxy;

/**
 * 真实主题
 */
public class TelReal implements Network {

	@Override
	public void browse() {
		System.out.println("电信网络");
	}

}
