package thread.sync;

/**
 * 问题引出：如果一个线程通过实现Runnable接口实现，则意味着类中的属性被多个线程共享，那么操作同一资源时可能会出现资源同步的问题。
 */
public class SyncDemo implements Runnable {

	private int ticket = 5;

	@Override
	public void run() {
		for (int i = 0; i < 50; i++) {
			if (ticket > 0) {
				// 为了突出问题，我们加入延迟操作，而且延迟的时间越长，问题越突出。
				try {
					Thread.sleep(400);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				System.out.println(Thread.currentThread().getName()
						+ "卖票：ticket = " + (ticket--));
			}
		}
	}

	public static void main(String[] args) {
		SyncDemo rDemo = new SyncDemo();
		new Thread(rDemo, "A").start();
		new Thread(rDemo, "B").start();
		new Thread(rDemo, "C").start();
		// 由于加入延迟操作，那么就有可能在一个线程还没有对票数进行减操作之前，其它线程就已经将票数减少了，所以就出现了票数为负数的情况。
	}

}
