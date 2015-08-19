package thread.prod.consu;

public class Info3 {

	// 给变量赋初始值是为了防止第一次执行时若是消费者先抢到CPU资源而先运行，导致内容没有赋值而为空的情况。
	private String name = "Sushiying";
	private String content = "AAAAAAA";
	private boolean flag;

	/**
	 * 为了解决问题1，我们加入synchronized关键字让设置姓名和内容的操作在同一个同步方法中完成。
	 * 
	 * @param name
	 * @param content
	 */
	public synchronized void set(String name, String content) {
		/*
		 * 为了解决问题2，我们加入Object类的唤醒与等待机制：如果为false，说明已经执行了一次set(String,
		 * String)方法，即已经生产发一次信息
		 * 。这时如果生产者线程再执行到该方法，就需要等待了，要等待消费者线程把对象取走，即把flag设置为true才能继续生产。
		 */
		if (!flag) {
			try {
				super.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		setName(name);
		// 为了让问题更好地呈现出来，我们加入延迟操作
		try {
			Thread.sleep(130);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		setContent(content);

		flag = false;
		super.notify();
	}

	/**
	 * 为了解决问题1，我们加入synchronized关键字让取出姓名和内容的操作在同一个同步方法中完成。
	 */
	public synchronized void get() {
		/*
		 * 为了解决问题2，我们加入Object类的唤醒与等待机制：与生产者等待与唤醒的原理相同。
		 */
		if (flag) {
			try {
				super.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// 为了让问题更好地呈现出来，我们加入延迟操作
		try {
			Thread.sleep(130);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(toString());

		flag = true;
		super.notify();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Info [name=" + name + ", content=" + content + "]";
	}

}
