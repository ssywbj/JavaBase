package function;

/**
 * 模拟零比特字节填充：发送数据时，如果一连串的数据中有连续的五个"1"，则在第五个"1"的后面添加一个“0”分隔数据再发送；而在接收到数据时，
 * 如果一连串的数据中有连续的五个"1"，则把第五个"1"的后面的“0”删除以还原数据。
 */
public class ZeroPadding {

	private int count = 0;

	protected String insertZero(String str) {
		StringBuffer sbf = new StringBuffer();
		char[] ch = str.toCharArray();

		for (int i = 0; i < ch.length; i++) {
			sbf.append(ch[i]);

			if (ch[i] == '1') {
				count++;// 每遇到“1”就自加一次
			}

			if (ch[i] == '0') {
				count = 0;// 一遇到“0”就清零，即重新计数
			}

			if (count == 5) {// 共计有五个连续的“1”后，就在后面多添加一个“0”
				sbf.append('0');
				count = 0;
			}
		}
		return sbf.toString();
	}

	protected String deleteZero(String str) {
		char[] ch = str.toCharArray();
		StringBuilder sbf = new StringBuilder();

		for (int x = 0; x < ch.length; x++) {
			sbf.append(ch[x]);

			if (ch[x] == '1') {
				count++;
			}

			if (ch[x] == '0') {
				count = 0;
			}

			if (count == 5) {
				count = 0;
				x++; // 因为每一次连续有五个“1”的后面肯定是“0”，所以x多一次自增的目的是为了不让“0”加入到sbf中
			}
		}
		return sbf.toString();
	}

	protected void print(String str) {
		char[] ch = str.toCharArray();
		for (char x : ch)
			System.out.print(x + " ");
	}

	public static void main(String args[]) {

		ZeroPadding zp = new ZeroPadding();

		// System.out.println("要发送的数据");
		String str1 = "101111111111010111111010111000000111110111111011";
		zp.print(str1);

		System.out.println();

		// System.out.println("实际发送的数据");
		String str2 = zp.insertZero(str1);
		zp.print(str2);

		System.out.println();

		// System.out.println("还原数据");
		zp.print(zp.deleteZero(str2));

		System.out.println();

		byte[] copy = "A我BCE是".getBytes();
		// 注意：copy.length 与 "A我BCE是".length()不相等，因为中文不止是用一个字节表示。
		byte[] source = new byte[2 + copy.length];
		source[0] = 0x46;// 十进制70
		source[1] = 0x47;// 十进制19
		print(source);
		/*
		 * copy：被复制的数组；0：被复制的数组从第1个开始复制；source：被复制到source数组；2：从source数组的第3个开始写入；copy
		 * .length：复制(copy.length)个元素到source数组。
		 */
		System.arraycopy(copy, 0, source, 2, copy.length);
		print(source);

		String data = new String(source);
		System.out.println("data.length() = " + data.length() + ", data = "
				+ data.trim());
		int initOffset = 1;
		// offset：从第(offset+1)个字节开始(包括这字节)；length：截取的长度，即截取多少个字节
		String data1 = new String(source, initOffset, 5);
		System.out.println("data1.length() = " + data1.length() + ", data1 = "
				+ data1.trim());

		byte[] data1Bytes = data1.getBytes();
		String data2 = new String(source, initOffset + data1Bytes.length,
				source.length - initOffset - data1Bytes.length);// 截取剩下的字节
		System.out.println("data2.length() = " + data2.length() + ", data2 = "
				+ data2.trim());
	}

	private static void print(byte[] data) {
		for (int i : data) {// 字节数组打印的是ASCII码
			System.out.print(i + " ");
		}
		System.out.println();
	}
}
