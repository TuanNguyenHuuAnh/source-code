package vn.com.unit.cms.admin.all.enumdef;

/**
 * AboutSerachEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author tuanh
 */
public enum InvestorKindEnum {
	
	KIND_0(0, "investor.kind.0"),

	KIND_1(1, "investor.kind.1"),

	KIND_2(2, "investor.kind.2"),

	KIND_3(3, "investor.kind.3"),

	KIND_4(4, "investor.kind.4"),

	KIND_5(5, "investor.kind.5"),

	KIND_6(6, "investor.kind.6"),

	KIND_7(7, "investor.kind.7");

	private Integer code;

	private String name;

	private InvestorKindEnum(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public String toString() {
		return code + ": " + name;
	}

	/**
	 * @return the code
	 * @author taitm
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 * @author taitm
	 */
	public void setCode(Integer code) {
		this.code = code;
	}

	/**
	 * @return the name
	 * @author taitm
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 * @author taitm
	 */
	public void setName(String name) {
		this.name = name;
	}

}
