/*******************************************************************************
 * Class        VietnameseHolidaySearchDto
 * Created date 2017/11/20
 * Lasted date  2017/11/20
 * Author       Phucdq
 * Change log   2017/11/2001-00 Phucdq create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.sla.dto;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * VietnameseHolidaySearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author Phucdq
 */
public class VietnameseHolidaySearchDto {

	/** fieldSearch */
    private String fieldSearch;

    /** languageCode */
    private String languageCode;

    /** fieldValues */
    private List<String> fieldValues;

    /** url */
    private String url;

    private Date vietnameseHolidayDate;

    private String description;
    
    private Boolean notFirst;

    /**
     * @return the fieldSearch
     */
    public String getFieldSearch() {
        return fieldSearch;
    }

    /**
     * @param fieldSearch
     *            the fieldSearch to set
     */
    public void setFieldSearch(String fieldSearch) {
        this.fieldSearch = fieldSearch;
    }

    /**
     * @return the languageCode
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * @param languageCode
     *            the languageCode to set
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     * @return the fieldValues
     */
    public List<String> getFieldValues() {
        return fieldValues;
    }

    /**
     * @param fieldValues
     *            the fieldValues to set
     */
    public void setFieldValues(List<String> fieldValues) {
        this.fieldValues = fieldValues;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the vietnameseHolidayDate
     */
    public Date getVietnameseHolidayDate() {
        return vietnameseHolidayDate;
    }

    /**
     * @param vietnameseHolidayDate
     *            the vietnameseHolidayDate to set
     */
    public void setVietnameseHolidayDate(Date vietnameseHolidayDate) {
        this.vietnameseHolidayDate = vietnameseHolidayDate;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /* trongcv 2017 oct 17 */
    private int year;
    private String firstDate;
    private String lastDate;

    public int getYear() {
        return year;

    }

    public void setYear(int year) {
        this.year = year;
        setFirstDate();
        setLastDate();
    }

    public String getFirstDate() {

        return firstDate;

    }

    public void setFirstDate() {
        String _firstDate = "01-01-" + Integer.toString(year);
        this.firstDate = _firstDate;
    }

    public String getLastDate() {

        return this.lastDate;

    }

    public void setLastDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONDAY, 12);
        cal.set(Calendar.DATE, 1);
        Integer end = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        String _lastDate = Integer.toString(end) + "-12-" + Integer.toString(year);
        this.lastDate = _lastDate;
    }
    
    private Long companyId;
    private Long calendarType;

	public Long getCalendarType() {
		return calendarType;
	}

	public void setCalendarType(Long calendarType) {
		this.calendarType = calendarType;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

    
    public Boolean getNotFirst() {
        return notFirst;
    }

    
    public void setNotFirst(Boolean notFirst) {
        this.notFirst = notFirst;
    }


}
