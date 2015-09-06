package function;

import java.text.SimpleDateFormat;

public class Time {

	public static void main(String[] args) {
		try {
			SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date begin = dfs.parse("2004-01-02 11:30:24");
			java.util.Date end = dfs.parse("2004-01-04 11:30:23");

			long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
			between = 161;
			long day = between / (24 * 3600);
			long hour = between % (24 * 3600) / 3600;
			long minute = between % 3600 / 60;
			long second = between % 60;
			System.out.println(new Time().new TimeEntity(between, day, hour,
					minute, second));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class TimeEntity {
		long between, day, hour, minute, second;

		public TimeEntity(long between) {
			this.between = between;
			divideTime(between);
		}

		private TimeEntity divideTime(long between) {
			long day = between / (24 * 3600);
			long hour = between % (24 * 3600) / 3600;
			long minute = between % 3600 / 60;
			long second = between % 60;
			return new TimeEntity(between, day, hour, minute, second);
		}

		public TimeEntity(long between, long day, long hour, long minute,
				long second) {
			this.between = between;
			this.day = day;
			this.hour = hour;
			this.minute = minute;
			this.second = second;
		}

		public long getBetween() {
			return between;
		}

		public void setBetween(long between) {
			this.between = between;
		}

		public long getDay() {
			return day;
		}

		public void setDay(long day) {
			this.day = day;
		}

		public long getHour() {
			return hour;
		}

		public void setHour(long hour) {
			this.hour = hour;
		}

		public long getMinute() {
			return minute;
		}

		public void setMinute(long minute) {
			this.minute = minute;
		}

		public long getSecond() {
			return second;
		}

		public void setSecond(long second) {
			this.second = second;
		}

		@Override
		public String toString() {
			return "TimeEntity [day=" + day + ", hour=" + hour + ", minute="
					+ minute + ", second=" + second + "]";
		}
	}

}
