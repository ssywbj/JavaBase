package function;

/**
 * 实现思想，按照中国人的读法，每四位分一组
 */
public class VoiceToText {
	private static final String[] SOURCE = { "零", "一", "二", "三", "四", "五", "六",
			"七", "八", "九" };
	private static final String[] BASIC = { "千", "百", "十", "" };
	private static final String[] UNIT = { "亿", "万", "元" };

	public static void main(String args[]) {
		voiceToText("151000596");
		voiceToText("080900096");
		voiceToText("102090.70");
		voiceToText("102090.8");
		voiceToText("102090.");
		voiceToText("3,547,803,192");
		voiceToText("3,547,803.19");

		// voiceToText("51000596");
		// voiceToText("18000096");
		// voiceToText("10290701");

		// voiceToText("5100596");
		// voiceToText("8000096");
		// voiceToText("1290701");

		// voiceToText("510596");
		// voiceToText("879096");
		// voiceToText("290701");
		// voiceToText("10596");
		// voiceToText("79096");
		// voiceToText("73001");
		// voiceToText("3596");
		// voiceToText("3096");
		// voiceToText("3006");
		// voiceToText("3000");
		// voiceToText("154");
		// voiceToText("104");
		// voiceToText("26");
		// voiceToText("20");
		// voiceToText("8");
	}

	private static void voiceToText(String voice) {
		if (voice != null && voice.length() > 0) {
			if (voice.matches("[1-9]\\d*")) {
				System.out.println("整数匹配(不能以零开头)");
			} else if (voice.matches("[1-9]\\d*\\.\\d{1,2}")) {
				System.out.println("最多带两位小数的匹配");
			} else if (voice.matches("[1-9]\\d{0,2}(,\\d{3})+(|\\.\\d{1,2})")) {
				if (voice.contains(".")) {
					System.out.println("逗号小数匹配");
				} else {
					System.out.println("逗号整数匹配");
				}
			} else {
				System.out.println("不匹配");
			}

			return;
		}

		String[] child = divideByLen(voice, 4, false);
		if (child != null) {
			StringBuilder total = new StringBuilder();
			int basicLen = BASIC.length;
			StringBuilder stringBuilder = null;
			boolean meetZero;
			int len = 0, count = 0;
			char ch;

			for (int j = 0; j < child.length; j++) {
				stringBuilder = new StringBuilder();
				len = child[j].length();
				meetZero = false;
				count = 0;

				for (int i = len; i > 0; i--, count++) {
					ch = child[j].charAt(count);
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

				total.append(stringBuilder.toString()).append(
						UNIT[UNIT.length - child.length + j]);
			}

			System.out.println(total.toString());
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

			return child;
		}

		return null;
	}

}
