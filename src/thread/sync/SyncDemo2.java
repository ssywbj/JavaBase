package thread.sync;

/**
 * 解决资源同步的问题，可以使用同步代码块和同步方法两种方式完成。
 * 同步：多个线程操作在同一个时间内只能有一个进行，其它线程要等待此线程完成之后才可以继承执行。
 */
public class SyncDemo2 implements Runnable {

	private int ticket = 5;
	private int ticket2 = 5;

	@Override
	public void run() {

		for (int i = 0; i < 50; i++) {
			// 第一种同步方式：加入同步代码块；使用同步代码块时，必须指定一个需要同步的对象，但一般都将当前对象(this)设置成同步对象。
			synchronized (this) {

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

		// 第二种同步方式：使用同步方法
		for (int i = 0; i < 50; i++) {
			sale();
		}

	}

	private synchronized void sale() {
		if (ticket2 > 0) {
			// 为了突出问题，我们加入延迟操作，而且延迟的时间越长，问题越突出。
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println(Thread.currentThread().getName()
					+ "卖票：ticket2 = " + (ticket2--));
		}
	}

	public static void main(String[] args) {
		SyncDemo2 rDemo = new SyncDemo2();
		new Thread(rDemo, "A").start();
		new Thread(rDemo, "B").start();
		new Thread(rDemo, "C").start();
		// 由于加入延迟操作，那么就有可能在一个线程还没有对票数进行减操作之前，其它线程就已经将票数减少了，所以就出现了票数为负数的情况。
	}

}
