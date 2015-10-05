package designmode.adapter;

/**
 * 适配器设。 问题提出：一个类要实现一个接口，则必须覆写接口中的全部抽象方法，
 * 但如果一个接口中定义的抽象方法过多，子类又用不到这么多抽象方法，我们该怎么办？
 */
public interface Window {
	public void open();

	public void close();

	public void activated();

	public void iconified();

	public void deiconified();
}
