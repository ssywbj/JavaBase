package function;

import java.util.Arrays;

public class BitDemo {

	public static void main(String args[]) {
		// 00 47
		// data[3] = 0, (data[3] & 0xff) = 0, ((data[3] & 0xff) << 8) = 0
		// data[4] = 71, (data[4] & 0xff) = 71
		// 02 3B
		// data[7] = 2, (data[7] & 0xff) = 2, ((data[7] & 0xff) << 8) = 512
		// data[8] = 59, (data[8] & 0xff) = 59
		byte b = 0;
		short b1 = 0xff;
		System.out.println("b1 = " + b1 + ", Byte.MIN_VALUE = "
				+ Byte.MIN_VALUE + ", Byte.MAX_VALUE = " + Byte.MAX_VALUE);
		int i = b & 0xff;// 按位与，相同为1，不同为0
		int i1 = i << 8;// 左移8位
		System.out.println("b & 0xff = " + i + ", i1 << 8 = " + i1);

		b = 3;
		System.out.println("(b << 9) = " + (b << 9));
		int j = b & 0xff;// 按位与，相同为1，不同为0
		int j1 = j << 8;// 左移8位
		System.out.println("b & 0xff = " + j + ", j1 << 8 = " + j1);

		byte o = (byte) 0x97;
		System.out.println("o = " + o);

		byte[] data = new byte[16];
		Arrays.fill(data, (byte) 0);// 数组全部填充0
		for (int k = 0; k < data.length; k++) {
			System.out.print(data[k] + " ");
		}

		System.out.println();

		int[] arr = { 4, 3, 6, 5, 2 };
		// Arrays.sort(arr);// 直接调用API排序
		bubbleSort(arr, true);
		for (int x : arr) {
			System.out.print(x + " ");
		}
		System.out.println();

		bubbleSort(arr, false);
		for (int x : arr) {
			System.out.print(x + " ");
		}
		System.out.println();
	}

	/**
	 * 冒泡排序
	 * 
	 * @param arr
	 *            要排序的数组
	 * @param headBig
	 *            true表示排序后返回的数组第一个元素为最大值，false表示最后一个元素为最大值
	 */
	private static void bubbleSort(int arr[], boolean headBig) {

		if (arr != null && arr.length > 0) {
			int temp;
			if (headBig) {
				for (int i = arr.length - 1; i > 0; --i) {
					for (int j = 0; j < i; ++j) {
						if (arr[j] < arr[j + 1]) {
							temp = arr[j];
							arr[j] = arr[j + 1];
							arr[j + 1] = temp;
						}
					}
				}
			} else {
				for (int i = arr.length - 1; i > 0; --i) {
					for (int j = 0; j < i; ++j) {
						if (arr[j] > arr[j + 1]) {
							temp = arr[j];
							arr[j] = arr[j + 1];
							arr[j + 1] = temp;
						}
					}
				}

			}
		}

	}

}
