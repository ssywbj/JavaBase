package object.demo;

/**
 * 在Java中，Object类是所有类的公共父类，所有的类的对象和任意的引用数据类型，都可能使用Object对象接收。
 */
class UnoverrideEquals {
	private String name;
	private int age;

	public UnoverrideEquals(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	// 未覆写equals(Object)方法
}

class OverrideEquals extends UnoverrideEquals {

	public OverrideEquals(String name, int age) {
		super(name, age);
	}

	/**
	 * 对象的判等操作：对象所在的类必须覆写equals(Object)方法来定义判等规则。注：String类已经覆写该方法。
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof OverrideEquals)) {
			return false;
		}

		OverrideEquals equals = (OverrideEquals) obj;
		if (this.getName().equals(equals.getName())
				&& this.getAge() == equals.getAge()) {
			return true;
		}

		return false;
	}

	// 未覆写hashCode()方法
}

class OverrideHashCode extends OverrideEquals {

	public OverrideHashCode(String name, int age) {
		super(name, age);
	}

	// 覆写hashCode()方法，并自定义对象HashCode的运算规则
	@Override
	public int hashCode() {
		return getName().hashCode() * getAge();
	}

}

public class EqualsDemo {

	public static void main(String[] args) {
		System.out.println("=======未覆写equals(Object)方法=======");
		UnoverrideEquals unEquals = new UnoverrideEquals("Sushiying", 18);
		UnoverrideEquals unEquals2 = new UnoverrideEquals("Sushiying", 18);
		// "=="比较的是对象的内存地址是否相同，也可以理解为是否是同一个对象
		System.out.println(unEquals == unEquals2);
		// equals(Object)是Object的方法，比较的是对象的值，默认时与"=="的作用相同，但我们可以重写该方法实现自定义的判等规则
		System.out.println(unEquals.equals(unEquals2));// 未覆写equals(Object)，equals(Object)与"=="的作用相同

		System.out.println("=======已覆写equals(Object)方法=======");
		OverrideEquals oEquals = new OverrideEquals("Sushiying", 18);
		OverrideEquals oEquals2 = new OverrideEquals("Sushiying", 18);
		System.out.println(oEquals == oEquals2);
		// 已覆写equals(Object)并定义了对象的判等规则，equals(Object)与"=="的作用不相同了，又因为两个对象的值一样，因此被认为是同一个对象。
		System.out.println(oEquals.equals(oEquals2));

		System.out.println("=======未覆写hashCode()方法=======");
		if (oEquals.hashCode() == oEquals2.hashCode()) {
			// 未覆写hashCode()方法，哪怕oEquals.equals(oEquals2)为true，但两个对象的hashCode还是不一样。
			System.out.println("两个对象的hashCode一样");
		} else {
			System.out.println("两个对象的hashCode不不不一样");
		}

		System.out.println("=======已覆写hashCode()方法=======");
		OverrideHashCode hashCode = new OverrideHashCode("Sushiying", 18);
		OverrideHashCode hashCode2 = new OverrideHashCode("Sushiying", 18);
		System.out.println(hashCode.equals(hashCode2));
		if (hashCode.hashCode() == hashCode2.hashCode()) {
			System.out.println("两个对象的hashCode一样");
		} else {
			/*
			 * 覆写hashCode()方法并自定义比较规则后，只要oEquals.equals(oEquals2)为true，
			 * 两个对象的hashCode就会一样。所以一个类覆写equals(Object)后，最好也要覆写hashCode()。
			 */
			System.out.println("两个对象的hashCode不不不一样");
		}

		System.out.println("=======通过HashCode判断是否是同一个对象=======");
		OverrideHashCode hashCode3 = new OverrideHashCode("Sushiying", 26);
		/*
		 * 覆写hashCode()方法并自定义比较规则后，只要oEquals.equals(oEquals2)为true，
		 * 两个对象的hashCode就会一样 。所以如果一个类覆写了equals(Object)， 最好也要覆写hashCode()，这样就能保证
		 * ：当obj1.equals(obj2)为true时，obj1.hashCode()
		 * =obj2.hashCode()；当obj1.hashCode() !=
		 * obj2.hashCode()时，obj1.equals(obj2)为false。
		 */
		if (hashCode.hashCode() == hashCode2.hashCode()) {// 因为两个对象HashCode一致，所以它们是同一个对象
			System.out.println(hashCode.equals(hashCode2));// true
		}
		if (hashCode.hashCode() != hashCode3.hashCode()) {// 因为两个对象HashCode不一致，所以它们不是同一个对象
			System.out.println(hashCode.equals(hashCode3));// false
		}
	}

}
