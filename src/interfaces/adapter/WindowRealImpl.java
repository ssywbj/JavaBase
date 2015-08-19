package interfaces.adapter;

public class WindowRealImpl extends WindowAdapter {

	@Override
	public void open() {
		super.open();
		System.out.println("窗口打开");
	}

	@Override
	public void close() {
		System.out.println("窗口关闭");
	}

	public static void main(String args[]) {
		WindowRealImpl window = new WindowRealImpl();
		window.open();
		window.activated();
		window.close();
	}

}
