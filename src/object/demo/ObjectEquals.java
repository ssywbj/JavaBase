package object.demo;

/**
 * 在Java中，Object类是所有类的公共父类，所有的类的对象和任意的引用数据类型，都可能使用Object对象接收。
 */
public class ObjectEquals {
	private String name;
	private int age;

	public ObjectEquals() {
	}

	public ObjectEquals(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "ObjectEquals [name=" + name + ", age=" + age + "]";
	}

	/**
	 * 对象的比较操作：对象所在的类必须覆写equals(Object)方法。String类已经覆写该方法
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ObjectEquals)) {
			return false;
		}

		ObjectEquals equals = (ObjectEquals) obj;
		if (this.name.equals(equals.name) && this.age == equals.age) {
			return true;
		}

		return false;
	}

	public static void main(String[] args) {
		ObjectEquals oEquals = new ObjectEquals("Sushiying", 18);
		ObjectEquals oEquals2 = new ObjectEquals("Sushiying", 18);
		ObjectEquals oEquals3 = new ObjectEquals("Sushiying", 25);
		System.out.println(oEquals.equals(oEquals2));// 对象的比较：两个对象的内容一致，被认为是同一个对象
		System.out.println(oEquals.equals(oEquals3));// 对象的比较：两个对象的内容不一致，被认为不是同一个对象
	}
}
