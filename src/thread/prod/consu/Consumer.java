package thread.prod.consu;

public class Consumer implements Runnable {

	private Info info;

	public Consumer(Info info) {
		this.info = info;
	}

	@Override
	public void run() {
		for (int i = 0; i < 50; i++) {
			// 为了让问题更好地呈现出来，我们加入延迟操作
			try {
				Thread.sleep(110);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(info);
		}

	}

}
