package thread.prod.consu;

public class Producer implements Runnable {

	private Info info;

	public Producer(Info info) {
		this.info = info;
	}

	@Override
	public void run() {
		boolean flag = false;

		for (int i = 0; i < 50; i++) {
			if (flag) {
				info.setName("Sushiying");
				// 为了让问题更好地呈现出来，我们加入延迟操作
				try {
					Thread.sleep(110);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				info.setContent("AAAAAAA");
				flag = false;
			} else {
				info.setName("Weibangjie");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				info.setContent("BBBBBBB");
				flag = true;
			}
		}

	}

}
