package thread.prod.consu;

public class Consumer2 implements Runnable {

	private Info2 info2;

	public Consumer2(Info2 info2) {
		this.info2 = info2;
	}

	@Override
	public void run() {
		for (int i = 0; i < 50; i++) {
			info2.get();
		}

	}

}
