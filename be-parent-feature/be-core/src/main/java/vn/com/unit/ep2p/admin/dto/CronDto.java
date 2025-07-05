package vn.com.unit.ep2p.admin.dto;

import java.util.Date;

/**
 * @author longpnt
 *
 */
public class CronDto {

	private static final String ASTERISK = "*";

	private static final String QUESTION_MARK = "?";

	private static final int DEFAULT_YEAR = 1900;

	private String seconds;
	private String minutes;
	private String hours;
	private String dayOfMonth;
	private String month;
	private String dayOfWeek;
	private String year;

	public CronDto() {
		this.seconds = ASTERISK;
		this.minutes = ASTERISK;
		this.hours = ASTERISK;
		this.dayOfMonth = ASTERISK;
		this.month = ASTERISK;
		this.dayOfWeek = QUESTION_MARK;
		this.year = ASTERISK;
	}

	@SuppressWarnings("deprecation")
	public CronDto addDate(Date date) {
		this.addSeconds(date.getSeconds())
				.addMinutes(date.getMinutes())
				.addHours(date.getHours())
				.addDayOfMonth(date.getDate())
				.addMonth(date.getMonth() + 1)
				.addYear(date.getYear() + DEFAULT_YEAR);
		return this;
	}

	public String getPattern() {
		return this.seconds + " " + this.minutes + " " + this.hours + " " + this.dayOfMonth + " " + this.month + " "
				+ this.dayOfWeek + " " + this.year;
	}

	public CronDto addSeconds(Object seconds) {
		this.seconds = String.valueOf(seconds);
		return this;
	}

	public CronDto addMinutes(Object minutes) {
		this.minutes = String.valueOf(minutes);
		return this;
	}

	public CronDto addHours(Object hours) {
		this.hours = String.valueOf(hours);
		return this;
	}

	public CronDto addDayOfMonth(Object dayOfMonth) {
		this.dayOfMonth = String.valueOf(dayOfMonth);
		return this;
	}

	public CronDto addMonth(Object month) {
		this.month = String.valueOf(month);
		return this;
	}

	public CronDto addDayOfWeek(Object dayOfWeek) {
		this.dayOfWeek = String.valueOf(dayOfWeek);
		return this;
	}

	public CronDto addYear(Object year) {
		this.year = String.valueOf(year);
		return this;
	}

	public String getSeconds() {
		return seconds;
	}

	public void setSeconds(String seconds) {
		this.seconds = seconds;
	}

	public String getMinutes() {
		return minutes;
	}

	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getDayOfMonth() {
		return dayOfMonth;
	}

	public void setDayOfMonth(String dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

}
