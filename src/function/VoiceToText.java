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

		voiceToText("35962");
		voiceToText("309645");
		// voiceToText("3006781");

	}

	private static void voiceToText(String voice) {
		if (voice.length() > 4) {
			int begin = 0, end = 4;

			
			String strLen4[] = voice.split("\\d{4}");
			if (strLen4 != null && strLen4.length > 0) {
				for (String child : strLen4) {
					System.out.println(child);
				}
			}
		} else {
			System.out.println(voice);
		}

		// if (voice != null && voice.length() > 0) {
		// StringBuilder stringBuilder = new StringBuilder();
		// int basicLen = BASIC.length, len = voice.length(), count = 0;
		// boolean meetZero = false;
		// char ch;
		// for (int i = len; i > 0; i--, count++) {
		// ch = voice.charAt(count);
		// if (ch == '0') {
		// meetZero = true;
		// } else {
		// if (meetZero) {
		// stringBuilder.append(SOURCE[0]);
		// }
		// stringBuilder.append(SOURCE[Integer.parseInt("" + ch)])
		// .append(BASIC[basicLen - i]);
		// meetZero = false;
		// }
		// }
		//
		// System.out.println(stringBuilder.toString());
		// }

	}

}
