package thread.stop;

import java.util.Timer;
import java.util.TimerTask;

public class StopRunable implements Runnable {

	private boolean isStopThread;
	private int mRumNmu = 0;

	@Override
	public void run() {

		while (!isStopThread) {
			// 在线程内部，自己结束自己
			// if (mRumNmu == 1000) {
			// stopThread();// 方法一：设置isStopThread为true，结束while循环
			// //
			// return;//方法二：使用return直接退出整个run方法，一旦执行到return，return往下的所有代码将不会执行。
			// }
			System.out.println(Thread.currentThread().getName() + " ,run = "
					+ (mRumNmu++));
		}

		// 一旦执行到这，说明run方法已经执行完毕，线程结束运行
		System.out.println(Thread.currentThread().getName() + " ,stop = "
				+ (mRumNmu));
	}

	public void stopThread() {
		isStopThread = true;
	}

	public static void main(String[] args) {
		final StopRunable stopRunable = new StopRunable();
		new Thread(stopRunable, "停止一个正在运行的线程").start();

		// 由于主线程和子线程是异步的，也就是并发执行，所以if中的条件还没来得及成立就被一直往下自执行
		if (stopRunable.mRumNmu == 1000) {
			System.out.println("不会执行到此处代码");
			stopRunable.stopThread();
		}

		// 在线程外部，通过达到某个条件而停止线程的运行
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			int countTime = 0;

			@Override
			public void run() {
				countTime++;
				if (countTime == 3) {
					stopRunable.stopThread();// 结束线程的运行
					timer.cancel();// 取消正在进行的任务
					timer.purge();
				}
			}
		}, 0, 400);

	}

}
