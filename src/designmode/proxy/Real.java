package designmode.proxy;

/**
 * 真实主题
 */
public class Real implements Network {

	@Override
	public void browse() {
		System.out.println("上网浏览信息");
	}

}
