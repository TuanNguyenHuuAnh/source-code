/*******************************************************************************
 * Class        ：CronDto
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * CronDto
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
@Setter
@Getter
public class CronDto {

	/** The Constant ASTERISK. */
	private static final String ASTERISK = "*";

	/** The Constant QUESTION_MARK. */
	private static final String QUESTION_MARK = "?";

	/** The Constant DEFAULT_YEAR. */
	private static final int DEFAULT_YEAR = 1900;

	/** The seconds. */
	private String seconds;
	
	/** The minutes. */
	private String minutes;
	
	/** The hours. */
	private String hours;
	
	/** The day of month. */
	private String dayOfMonth;
	
	/** The month. */
	private String month;
	
	/** The day of week. */
	private String dayOfWeek;
	
	/** The year. */
	private String year;

	/**
     * <p>
     * Instantiates a new cron dto.
     * </p>
     *
     * @author khadm
     */
	public CronDto() {
		this.seconds = ASTERISK;
		this.minutes = ASTERISK;
		this.hours = ASTERISK;
		this.dayOfMonth = ASTERISK;
		this.month = ASTERISK;
		this.dayOfWeek = QUESTION_MARK;
		this.year = ASTERISK;
	}

	/**
     * <p>
     * Adds the date.
     * </p>
     *
     * @author khadm
     * @param date
     *            type {@link Date}
     * @return {@link CronDto}
     */
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

	/**
     * <p>
     * Get pattern.
     * </p>
     *
     * @author khadm
     * @return {@link String}
     */
	public String getPattern() {
		return this.seconds + " " + this.minutes + " " + this.hours + " " + this.dayOfMonth + " " + this.month + " "
				+ this.dayOfWeek + " " + this.year;
	}

	/**
     * <p>
     * Adds the seconds.
     * </p>
     *
     * @author khadm
     * @param seconds
     *            type {@link Object}
     * @return {@link CronDto}
     */
	public CronDto addSeconds(Object seconds) {
		this.seconds = String.valueOf(seconds);
		return this;
	}

	/**
     * <p>
     * Adds the minutes.
     * </p>
     *
     * @author khadm
     * @param minutes
     *            type {@link Object}
     * @return {@link CronDto}
     */
	public CronDto addMinutes(Object minutes) {
		this.minutes = String.valueOf(minutes);
		return this;
	}

	/**
     * <p>
     * Adds the hours.
     * </p>
     *
     * @author khadm
     * @param hours
     *            type {@link Object}
     * @return {@link CronDto}
     */
	public CronDto addHours(Object hours) {
		this.hours = String.valueOf(hours);
		return this;
	}

	/**
     * <p>
     * Adds the day of month.
     * </p>
     *
     * @author khadm
     * @param dayOfMonth
     *            type {@link Object}
     * @return {@link CronDto}
     */
	public CronDto addDayOfMonth(Object dayOfMonth) {
		this.dayOfMonth = String.valueOf(dayOfMonth);
		return this;
	}

	/**
     * <p>
     * Adds the month.
     * </p>
     *
     * @author khadm
     * @param month
     *            type {@link Object}
     * @return {@link CronDto}
     */
	public CronDto addMonth(Object month) {
		this.month = String.valueOf(month);
		return this;
	}

	/**
     * <p>
     * Adds the day of week.
     * </p>
     *
     * @author khadm
     * @param dayOfWeek
     *            type {@link Object}
     * @return {@link CronDto}
     */
	public CronDto addDayOfWeek(Object dayOfWeek) {
		this.dayOfWeek = String.valueOf(dayOfWeek);
		return this;
	}

	/**
     * <p>
     * Adds the year.
     * </p>
     *
     * @author khadm
     * @param year
     *            type {@link Object}
     * @return {@link CronDto}
     */
	public CronDto addYear(Object year) {
		this.year = String.valueOf(year);
		return this;
	}

}
