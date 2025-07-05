/*******************************************************************************
 * Class        ：StatusEnum
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.enumdef;

/**
 * <p>
 * StatusEnum
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
public enum StatusEnum {
	
	/** The actived. */
	ACTIVED(1, "valid.flag.status.active"), 
	
	/** The history. */
	HISTORY(2, "valid.flag.status.history"),
	
	/** The waiting. */
	WAITING(3, "valid.flag.status.waiting"),
	
	/** The canceled. */
	CANCELED(4, "valid.flag.status.canceled"), 
	
	/** The rejected. */
	REJECTED(5, "valid.flag.status.rejected"),
	
	/** The deleted. */
	DELETED(6, "valid.flag.request.type.delete"),
	
	/** The draft. */
	DRAFT(7, "master.status.savedraff"),
	
	/** The bo. */
	BO(8, "");
	
	/** The value. */
	private Integer value;
	
	/** The name. */
	private String name;
	
	/**
     * <p>
     * Instantiates a new status enum.
     * </p>
     *
     * @author khadm
     * @param value
     *            type {@link Integer}
     * @param name
     *            type {@link String}
     */
	private StatusEnum(Integer value, String name) {
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
	
	/**
     * <p>
     * Get list search.
     * </p>
     *
     * @author khadm
     * @return {@link StatusEnum[]}
     */
	public static StatusEnum[] getListSearch() {
		StatusEnum[] searchEnums = {WAITING, REJECTED};
		return searchEnums;
	}
	
}
