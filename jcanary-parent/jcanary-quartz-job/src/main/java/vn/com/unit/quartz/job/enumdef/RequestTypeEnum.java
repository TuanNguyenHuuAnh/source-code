/*******************************************************************************
 * Class        ：RequestTypeEnum
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.enumdef;

/**
 * <p>
 * RequestTypeEnum
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
public enum RequestTypeEnum {
	
	/** The new. */
	NEW(0, "valid.flag.request.type.new"), 
	
	/** The update. */
	UPDATE(1, "valid.flag.request.type.update"),
	
	/** The delete. */
	DELETE(2, "valid.flag.request.type.delete");
	
	/** The value. */
	private Integer value;
	
	/** The name. */
	private String name;
	
	/**
     * <p>
     * Instantiates a new request type enum.
     * </p>
     *
     * @author khadm
     * @param value
     *            type {@link Integer}
     * @param name
     *            type {@link String}
     */
	private RequestTypeEnum(Integer value, String name) {
		this.value = value;
		this.name = name;
	}
	
	/**
     * <p>
     * Get value.
     * </p>
     *
     * @author khadm
     * @return {@link Integer}
     */
	public Integer getValue() {
		return value;
	}
	
	/**
     * <p>
     * Get name.
     * </p>
     *
     * @author khadm
     * @return {@link String}
     */
	public String getName() {
		return name;
	}
	
}
