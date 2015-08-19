package thread.prod.consu;

public class Info2 {

	// 给变量赋初始值是为了防止第一次执行时若是消费者先抢到CPU资源而先运行，导致内容没有赋值而为空的情况。
	private String name = "Sushiying";
	private String content = "AAAAAAA";

	/**
	 * 为了解决问题1，我们加入synchronized关键字让设置姓名和内容的操作在同一个同步方法中完成。
	 * 
	 * @param name
	 * @param content
	 */
	public synchronized void set(String name, String content) {
		setName(name);
		// 为了让问题更好地呈现出来，我们加入延迟操作
		try {
			Thread.sleep(130);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		setContent(content);
	}

	/**
	 * 为了解决问题1，我们加入synchronized关键字让取出姓名和内容的操作在同一个同步方法中完成。
	 */
	public synchronized void get() {
		// 为了让问题更好地呈现出来，我们加入延迟操作
		try {
			Thread.sleep(130);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(toString());
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
