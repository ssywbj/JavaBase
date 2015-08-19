package thread.diff;

/**
 * 通过Thread类和Runnable接口实现多线程操作的不同：如果一个类通过继承Thread类实现多线程操作，则不适合用于多个线程共享某个资源的情况，
 * 而实现Runnable接口可以很好地达到共享资源的目的。
 * 如以下的Demo中，程序启动了三个线程，但三个线程却分别卖出了各自的五张票，也就是说每个对象都有各自的ticket属性，从而并没有达到资源共享的目的。
 */
public class ThreadDemo extends Thread {

	private int ticket = 5;

	public ThreadDemo(String name) {
		super(name);
	}

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
		new ThreadDemo("A").start();
		new ThreadDemo("B").start();
		new ThreadDemo("C").start();
	}

}
