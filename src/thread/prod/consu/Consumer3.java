package thread.prod.consu;

public class Consumer3 implements Runnable {

	private Info3 info3;

	public Consumer3(Info3 info3) {
		this.info3 = info3;
	}

	@Override
	public void run() {
		for (int i = 0; i < 50; i++) {
			info3.get();
		}

	}

}
