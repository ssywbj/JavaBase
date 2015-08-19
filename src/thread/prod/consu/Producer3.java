package thread.prod.consu;

public class Producer3 implements Runnable {

	private Info3 info3;

	public Producer3(Info3 info3) {
		this.info3 = info3;
	}

	@Override
	public void run() {
		boolean flag = false;

		for (int i = 0; i < 50; i++) {
			if (flag) {
				info3.set("Sushiying", "AAAAAAA");
				flag = false;
			} else {
				info3.set("Weibangjie", "BBBBBBB");
				flag = true;
			}
		}

	}

}
