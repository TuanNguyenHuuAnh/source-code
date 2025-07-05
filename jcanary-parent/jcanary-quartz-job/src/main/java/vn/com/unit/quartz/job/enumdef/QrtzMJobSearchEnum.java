/*******************************************************************************
 * Class        ：QrtzMJobSearchEnum
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.enumdef;

/**
 * <p>
 * QrtzMJobSearchEnum
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
public enum QrtzMJobSearchEnum {

	/** The job code. */
	JOB_CODE("quartz.field.job.code"),

	/** The job name. */
	JOB_NAME("quartz.table.job.name"),

	/** The job type. */
	JOB_TYPE("quartz.field.job.type"),

	/** The job class name. */
	JOB_CLASS_NAME("quartz.field.job.class.name");

	//JOB_STORE("quartz.field.job.store.procedure");

	/** The value. */
	private String value;

	/**
     * <p>
     * Instantiates a new qrtz M job search enum.
     * </p>
     *
     * @author khadm
     * @param value
     *            type {@link String}
     */
	private QrtzMJobSearchEnum(String value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	public String toString() {
		return value;
	}
}
