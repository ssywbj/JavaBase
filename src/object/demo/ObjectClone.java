package object.demo;

/**
 * 对象的克隆操作：对象的复制，即完整地复制一个对象。要完成对象的克隆操作，对象所在的类必须实现Cloneable接口并覆写Object类的clone()
 * 方法。
 */
public class ObjectClone implements Cloneable {
	private String name;
	private int age;

	public ObjectClone() {
	}

	public ObjectClone(String name, int age) {
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
		return "ObjectClone [name=" + name + ", age=" + age + "]";
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();// 具体的克隆操作由父类完成
	}

	public static void main(String[] args) {
		try {
			ObjectClone oc = new ObjectClone("Sushiying", 18);
			ObjectClone clone = (ObjectClone) oc.clone();
			System.out.println(oc);
			clone.setName("Weibangjie");
			clone.setAge(25);
			System.out.println(clone);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		short value = 3;
		short value16 = 0xff;//
	}
}
