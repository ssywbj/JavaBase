package interfaces.usb;

public class Print implements USB {

	@Override
	public void start() {
		System.out.println("Printer Start Work");
	}

	@Override
	public void stop() {
		System.out.println("Printer Stop Work");
	}

}
