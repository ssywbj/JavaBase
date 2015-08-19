package thread.prod.consu;

public class ThreadDemo2 {

	public static void main(String[] args) {
		Info2 info2 = new Info2();
		Producer2 pro2 = new Producer2(info2);
		Consumer2 con2 = new Consumer2(info2);
		new Thread(pro2).start();
		new Thread(con2).start();
	}

}
