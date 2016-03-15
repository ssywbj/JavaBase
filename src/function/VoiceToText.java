package function;

/**
 * 实现思想，按照中国人的读法，每四位分一组
 */
public class VoiceToText {
	private static final String[] SOURCE = { "零", "一", "二", "三", "四", "五", "六",
			"七", "八", "九" };
	private static final String[] BASIC = { "千", "百", "十", "" };

	public static void main(String args[]) {
		// voiceToText("3596");
		// voiceToText("3096");
		// voiceToText("3006");
		// voiceToText("3000");
		// voiceToText("154");
		// voiceToText("104");
		// voiceToText("26");
		// voiceToText("20");
		// voiceToText("8");

		divideByLen("3596", 5, true);
		divideByLen("13596", 5, true);
		divideByLen("1234596", 4, false);
		divideByLen("123435968", 4, false);
		divideByLen("123453596", 4, true);
	}

	private static void voiceToText(String voice) {
		if (voice != null && voice.length() > 0) {
			StringBuilder stringBuilder = new StringBuilder();
			int basicLen = BASIC.length, len = voice.length(), count = 0;
			boolean meetZero = false;
			char ch;
			for (int i = len; i > 0; i--, count++) {
				ch = voice.charAt(count);
				if (ch == '0') {
					meetZero = true;
				} else {
					if (meetZero) {
						stringBuilder.append(SOURCE[0]);
					}
					stringBuilder.append(SOURCE[Integer.parseInt("" + ch)])
							.append(BASIC[basicLen - i]);
					meetZero = false;
				}
			}

			System.out.println(stringBuilder.toString());
		}

	}

	private static String[] divideByLen(String source, int len, boolean fromHead) {
		if (len > 0 && source != null && source.length() > 0) {
			int strLen = source.length();
			int arrLen = (strLen % len == 0 ? strLen / len : strLen / len + 1);
			String[] child = new String[arrLen];
			if (fromHead) {
				for (int i = 0; i < arrLen; i++) {
					child[i] = source.substring(len * i,
							len * (i + 1) > strLen ? strLen : len * (i + 1));
				}
			} else {
				int beginIndex = 0;
				for (int i = 0; i < arrLen; i++) {
					beginIndex = strLen - len * (i + 1);
					child[arrLen - i - 1] = source.substring(
							beginIndex > 0 ? beginIndex : 0, strLen - len * i);
				}
			}

			for (String ch : child) {
				System.out.print(ch + "\t");
			}
			System.out.println();

			return child;
		}

		return null;
	}
}
