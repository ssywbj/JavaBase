package interfaces.usb;

/**
 * 使用接口来制定标准。场景实例：因为U盘和打印机都实现了USB接口，所以它们都可以插在计算机上使用；而对于计算机来说，
 * 只要是符合了USB接口标准的设备就可以插进来。
 */
public interface USB {

	public void start();

	public void stop();
}
