package designmode.single;

/**
 * 单例设计模式：将类的构造方法私有化，在类的内部产生实例化对象，之后在类的外部通过类的静态方法返回实例化对象的引用。
 */
public class SingleMode {
	private static SingleMode instance = new SingleMode();// 单例设计模式，在类的内部产生实例化对象
	private static int singleMode;
	private static int simpleMode;

	public static SingleMode getInstance() {
		return instance;
	}

	private SingleMode() { // 单例设计模式，将类的构造方法私有化
		print(++singleMode);
	}

	public SingleMode(String flag) {// 普通设计模式
		print(++simpleMode);
	}

	public void print(int i) {
		System.out.println(SingleMode.class.getSimpleName() + "_" + i);
	}

	public static void main(String[] args) {
		SingleMode.getInstance();// 单例设计模式，在类的外部通过类的静态方法返回实例化对象的引用
		SingleMode.getInstance();
		SingleMode.getInstance();
		/*
		 * 对比单例设计模式与普通设计模式的区别：单例设计只会输出一次，而普通设计会输出多次，这说明了单例设计只调用了一次SingleMode()方法，
		 * 所以有且只有产生了一个对象，也就是说不管在SingleMode类实例化了多少个对象，
		 * 程序最终结果也只有一个SingleMode类的实例化对象存在
		 * ，而普通设计模式每一次对象的产生都会调用SingleMode(String)方法，会产生多个对象，故会多次输出。
		 */
		String diff = "different";// 普通设计模式
		new SingleMode(diff);
		new SingleMode(diff);
		new SingleMode(diff);
	}

}
