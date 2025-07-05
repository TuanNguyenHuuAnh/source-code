package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class InterestRateDto {

	/**
	 * interest rate id
	 */
	private Long id;
	
	/**
	 * display date
	 */
	private Date displayDate;
	
	/**
	 * term id
	 */
	private Long termId;
	
	/**
	 * term code
	 */
	private String termCode;
	
	/**
	 * term name
	 */
	private String termName;
	
	/**
	 * personal currency list
	 */
	private Map<String, String> personalCurrencyList = new HashMap<String, String>();
	
	/**
	 * organisation currency list
	 */
	private Map<String, String> organisationCurrencyList = new HashMap<String, String>();

	/**
	 * get interest rate id
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * set interest rate id
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * get display date
	 * @return
	 */
	public Date getDisplayDate() {
		return displayDate;
	}

	/**
	 * set display date
	 * @param displayDate
	 */
	public void setDisplayDate(Date displayDate) {
		this.displayDate = displayDate;
	}

	/**
	 * get term id
	 * @return
	 */
	public Long getTermId() {
		return termId;
	}

	/**
	 * get term id
	 * @param termId
	 */
	public void setTermId(Long termId) {
		this.termId = termId;
	}

	/**
	 * get term code
	 * @return
	 */
	public String getTermCode() {
		return termCode;
	}

	/**
	 * set term code
	 * @param termCode
	 */
	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	/**
	 * get term name
	 * @return
	 */
	public String getTermName() {
		return termName;
	}

	/**
	 * set term name
	 * @param termName
	 */
	public void setTermName(String termName) {
		this.termName = termName;
	}

	/**
	 * get personal currency map
	 */
	public Map<String, String> getPersonalCurrencyList() {
		return personalCurrencyList;
	}

	/**
	 * set personal currency map
	 * @param personalCurrencyList
	 */
	public void setPersonalCurrencyList(Map<String, String> personalCurrencyList) {
		this.personalCurrencyList = personalCurrencyList;
	}

	/**
	 * get organisation currency map
	 * @return
	 */
	public Map<String, String> getOrganisationCurrencyList() {
		return organisationCurrencyList;
	}

	/**
	 * set organisation currency map
	 * @param organisationCurrencyList
	 */
	public void setOrganisationCurrencyList(Map<String, String> organisationCurrencyList) {
		this.organisationCurrencyList = organisationCurrencyList;
	}
}
