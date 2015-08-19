package thread.prod.consu;

public class Info {

	// 给变量赋初始值是为了防止第一次执行时若是消费者先抢到CPU资源而先运行，导致内容没有赋值而为空的情况。
	private String name = "Sushiying";
	private String content = "AAAAAAA";

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
