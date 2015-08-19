package thread.prod.consu;

public class ThreadDemo {

	public static void main(String[] args) {
		Info info = new Info();
		Producer pro = new Producer(info);
		Consumer con = new Consumer(info);
		new Thread(pro).start();
		new Thread(con).start();
		/*
		 * 以上程序存在两个问题：1.Info对象的内容匹配错误，即"Sushiying"每次应和"AAAAAAA"一起出现，有时候却和"BBBBBBB"
		 * 一起出现；
		 * 2.Info的内容重复出现，理应是生产一次就取走一次。问题1产生的原因：生产者线程刚向数据存储空间添加了信息的名称，还没有来得及加入该信息的内容
		 * ，程序就切换到了消费者线程，消费者就会将信息的名称和上一个信息的内容联系在一起；
		 * 问题2产生的原因：生产者放了若干次的数据，消费者才开始取数据
		 * ，或者是消费者取完一个数据后，还没有等到生产者放入新的数据，又重复取出已取过的数据。
		 */
	}

}
