package designmode.observer;

import java.util.Observable;
import java.util.Observer;

public class HouseObserverC implements Observer {

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
