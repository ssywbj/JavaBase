package designmode.observer;

import java.util.Observable;

/**
 * 观察者设计模式。House：被观察的对象
 */
public class House extends Observable {
	private float price;

	public House(float price) {
		this.price = price;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
		super.setChanged();// 设置变化点，每一次修改都会引起观察者的注意
		super.notifyObservers(price);// 通知价格被修改
	}

	public static void main(String[] args) {
		House house = new House(123.4f);

		house.addObserver(new HouseObserverC());// 添加观察者
		house.addObserver(new HouseObserverB());// 添加观察者
		house.addObserver(new HouseObserverA());// 添加观察者

		house.setPrice(203.9f);

	}
}
