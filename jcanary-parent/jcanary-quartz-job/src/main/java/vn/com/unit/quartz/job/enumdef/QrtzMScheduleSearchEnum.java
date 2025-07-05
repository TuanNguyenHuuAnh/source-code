/*******************************************************************************
 * Class        ：QrtzMScheduleSearchEnum
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.enumdef;

/**
 * <p>
 * QrtzMScheduleSearchEnum
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
public enum QrtzMScheduleSearchEnum {

	/** The sched code. */
	SCHED_CODE("quartz.field.schedule.code"),

	/** The sched name. */
	SCHED_NAME("quartz.table.schedule.name"),

	/** The cron expression. */
	CRON_EXPRESSION("quartz.field.cron"),

	/** The description. */
	DESCRIPTION("quartz.field.table.description");

	/** The value. */
	private String value;

	/**
     * <p>
     * Instantiates a new qrtz M schedule search enum.
     * </p>
     *
     * @author khadm
     * @param value
     *            type {@link String}
     */
	private QrtzMScheduleSearchEnum(String value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	public String toString() {
		return value;
	}
}
