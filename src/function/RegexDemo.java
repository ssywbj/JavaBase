package function;

public class RegexDemo {
	private static final String REFEX_DECIMAL = "\\d+.{0,1}\\d{0,2}";// 最多有两位小数
	private static final String REFEX_DATE = "\\d{4}-\\d{2}-\\d{2}";
	private static final String REFEX_EMAIL = "\\w{5,}@[a-z0-9]{2,4}.[a-z]{2,3}";

	public static void main(String args[]) {
		validateDecimal("");
		validateDecimal("3");
		validateDecimal("33");
		validateDecimal("3.4");
		validateDecimal("3..");
		validateDecimal("3..4");
		validateDecimal("3366666613");
		validateDecimal("36313.");
		validateDecimal("33013.3");
		validateDecimal("3313.34");
		validateDecimal("3313.347");
		System.out.println();

		validateDate("1987-04-09");
		validateDate("1987- 04-9");
		System.out.println();

		validateEmail("1271829012@qq.com");
		validateEmail("1_718q9d12@qq.com");
		validateEmail("9d12@qq.com");
		validateEmail("rss9d12@qqeq7.com");
		validateEmail("rss9d12@qqeq.n");
		System.out.println();

		spiltString();
	}

	/**
	 * @param str
	 */
	public static void validateDecimal(String str) {
		if (str.matches(REFEX_DECIMAL)) {
			System.out.println(str + " 数字输入合法");
		} else {
			System.out.println(str + " 数字输入不合法");
		}
	}

	private static void spiltString() {
		String source = "CCD64FF09LL2YY8QQQ876i";
		String[] split = source.split("\\d+");
		for (String str : split) {
			System.out.print(str + "\t");
		}
		System.out.println();

		String regex = "[(\\d,\\d)\\|]{1,}";
		source = "(1,2)|(3,4)|(5,6)";
		if (source.matches(regex)) {
			split = source.split("\\|");
			for (String str : split) {
				System.out.print(str + "\t");
			}
			System.out.println();
		} else {
			System.out.println("要拆分的字符串不合法");
		}
	}

	private static void validateEmail(String str) {
		if (str.matches(REFEX_EMAIL)) {
			System.out.println("邮箱输入合法");
		} else {
			System.out.println("邮箱输入不合法");
		}
	}

	private static void validateDate(String str) {
		if (str.matches(REFEX_DATE)) {
			System.out.println("日期输入合法");
		} else {
			System.out.println("日期输入不合法");
		}
	}

}
