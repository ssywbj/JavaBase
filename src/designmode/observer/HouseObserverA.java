package designmode.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * 观察者设计模式。HouseObserverA：观察者A
 */
public class HouseObserverA implements Observer {
	/**
	 * o：被观察的对象；arg：被观察的内容。
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof House) {
			if (arg instanceof Float) {
				float price = ((Float) arg).floatValue();
				System.out.println(getClass().getSimpleName() + "_" + price);
			}
		}
	}

}
