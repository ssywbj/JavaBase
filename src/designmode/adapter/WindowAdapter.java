package designmode.adapter;

/**
 * 问题的解决：定义一个中间的过渡类。但是该过渡类又不希望被直接使用，所以将此过渡类定义为抽象类最合适，即接口先被一个抽象类(此抽象类通常称为适配器类)实现，
 * 并在抽象类中实现接口的若干方法(方法体为空 )，再由子类继承该抽象类，则在子类就可以有选择地覆写所需要的方法了。
 */
public abstract class WindowAdapter implements Window {
	@Override
	public void open() {
		// 方法体为空
	}

	// 该抽象类并未实现close()方法

	@Override
	public void activated() {
		System.out.println("用于测试输出，方法体理应为空！！");
	}

	@Override
	public void iconified() {
	}

	@Override
	public void deiconified() {
	}

}
