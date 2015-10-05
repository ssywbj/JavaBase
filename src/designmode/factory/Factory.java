package designmode.factory;

/**
 * 工厂类：一个专门用于产生接口的实例化对象的类
 */
public class Factory {

	public static Fruit getInstance(String className) {
		Fruit fruit = null;
		// 根据传入的className的不同，用不同的子类去实例化Fruit接口
		if ("Apple".equals(className)) {
			fruit = new Apple();
		} else if ("Orange".equals(className)) {
			fruit = new Orange();
		}
		return fruit;
	}

	public static void main(String[] args) {
		Fruit fruit = Factory.getInstance("Apple");
		if (fruit != null) {
			fruit.eat();
		}

		System.out.println("=============");

		fruit = Factory.getInstance("Orange");
		if (fruit != null) {
			fruit.eat();
		}
	}

}
