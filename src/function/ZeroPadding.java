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
		int len = ch.length;

		for (int i = 0; i < len; i++) {
			sbf.append(ch[i]);

			if (ch[i] == '1') {
				count++;// 每遇到“1”就自加一次
			}

			if (ch[i] == '0') {
				count = 0;// 每遇到“0”就清零，即重新计数
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
		int len = ch.length;

		for (int x = 0; x < len; x++) {
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
	}
}
