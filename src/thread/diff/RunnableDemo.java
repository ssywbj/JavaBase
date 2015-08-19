package thread.diff;

/**
 * 通过实现Runnable接口可以很好地实现资源共享。如以下的Demo中，虽然程序启动了三个线程，但是三个线程一共才卖出五张票，
 * 也就是说ticket属性被所有的线程对象共有，从而达到资源共享的目的。
 * 使用Runnable接口实现多线程操作的好处：1.适合多个相同程序代码的线程去处理同一资源的情况 ；2.可以避免由于Java的单继承特性带来的局限；
 * 3.增强了程序的健壮性，代码能被多个线程共享，代码与数据是独立的。
 */
public class RunnableDemo implements Runnable {

	private int ticket = 5;

	@Override
	public void run() {
		for (int i = 0; i < 50; i++) {
			if (ticket > 0) {
				System.out.println(Thread.currentThread().getName()
						+ "卖票：ticket = " + (ticket--));
			}
		}
	}

	public static void main(String[] args) {
		RunnableDemo rDemo = new RunnableDemo();
		new Thread(rDemo, "A").start();
		new Thread(rDemo, "B").start();
		new Thread(rDemo, "C").start();
	}

}
