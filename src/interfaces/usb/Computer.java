package interfaces.usb;

public class Computer {

	/**
	 * 接收USB的子类：传入的是哪个子类，就执行它覆写后的方法。
	 */
	public void plugin(USB usb) {
		usb.start();
		usb.stop();
	}

	public static void main(String[] args) {
		new Computer().plugin(new Flash());
		System.out.println("=================");
		new Computer().plugin(new Print());
	}

}
