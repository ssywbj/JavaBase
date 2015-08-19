package thread.prod.consu;

public class ThreadDemo3 {

	public static void main(String[] args) {
		Info3 info3 = new Info3();
		Producer3 pro3 = new Producer3(info3);
		Consumer3 con3 = new Consumer3(info3);
		new Thread(pro3).start();
		new Thread(con3).start();
	}

}
