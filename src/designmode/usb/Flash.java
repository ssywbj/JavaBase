package designmode.usb;

public class Flash implements USB {

	@Override
	public void start() {
		System.out.println("Flash Start Work");
	}

	@Override
	public void stop() {
		System.out.println("Flash Stop Work");
	}

}
