package designmode.proxy;

/**
 * 代理主题
 */
public class Proxy implements Network {

	private Network network;

	public Proxy(Network network) {
		this.network = network;
	}

	@Override
	public void browse() {
		check();
		network.browse();
	}

	private void check() {
		System.out.println("检查用户信息是否合法");
	}

	public static void main(String[] args) {
		Network network = new Proxy(new Real());// 在代理主题的构造方法中传入真实主题，这样就能够让代理主题操作真实主题了。
		if (network != null) {
			network.browse();
		}
	}

}
