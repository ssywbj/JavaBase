package thread.prod.consu;

public class Producer2 implements Runnable {

	private Info2 info2;

	public Producer2(Info2 info2) {
		this.info2 = info2;
	}

	@Override
	public void run() {
		boolean flag = false;

		for (int i = 0; i < 50; i++) {
			if (flag) {
				info2.set("Sushiying", "AAAAAAA");
				flag = false;
			} else {
				info2.set("Weibangjie", "BBBBBBB");
				flag = true;
			}
		}

	}

}
