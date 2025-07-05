/*******************************************************************************
 * Class        ：JobSearchDto
 * Created date ：2017/03/22
 * Lasted date  ：2017/03/22
 * Author       ：TranLTH
 * Change log   ：2017/03/22：01-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.List;

/**
 * JobSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public class JobSearchDto {
    /** fieldSearch */
    private String fieldSearch;

    /** languageCode */
    private String languageCode;

    /** fieldValues */
    private List<String> fieldValues;
    
    /** code */
    private String code;

    /** name */
    private String name;

    /** note */
    private String note;
    
    /** career */
    private String career;
    
    /** position */
    private String position;
    
    /** expiry date */    
    private Date expiryDate;
    
    /** url*/
    private String url;
    
    private String positionName;
    
    private String experience;
    
    private String location;
    
    private String division;
    
    private String status;
    
    private List<String> listCareer;
    
    private List<String> listDivision;
    
    private List<String> listLocation;
    
    private String jobTitle;
    
    private String createBy;
    
    private Date expiryDateFrom;
    
    private Date expiryDateTo;

    /**
     * Get fieldSearch
     * @return String
     * @author TranLTH
     */
    public String getFieldSearch() {
        return fieldSearch;
    }

    /**
     * Set fieldSearch
     * @param   fieldSearch
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setFieldSearch(String fieldSearch) {
        this.fieldSearch = fieldSearch;
    }

    /**
     * Get languageCode
     * @return String
     * @author TranLTH
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Set languageCode
     * @param   languageCode
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     * Get fieldValues
     * @return List<String>
     * @author TranLTH
     */
    public List<String> getFieldValues() {
        return fieldValues;
    }

    /**
     * Set fieldValues
     * @param   fieldValues
     *          type List<String>
     * @return
     * @author  TranLTH
     */
    public void setFieldValues(List<String> fieldValues) {
        this.fieldValues = fieldValues;
    }

    /**
     * Get code
     * @return String
     * @author TranLTH
     */
    public String getCode() {
        return code;
    }

    /**
     * Set code
     * @param   code
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Get name
     * @return String
     * @author TranLTH
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     * @param   name
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get note
     * @return String
     * @author TranLTH
     */
    public String getNote() {
        return note;
    }

    /**
     * Set note
     * @param   note
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Get career
     * @return String
     * @author TranLTH
     */
    public String getCareer() {
        return career;
    }

    /**
     * Set career
     * @param   career
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setCareer(String career) {
        this.career = career;
    }

    /**
     * Get position
     * @return String
     * @author TranLTH
     */
    public String getPosition() {
        return position;
    }

    /**
     * Set position
     * @param   position
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Get expiryDate
     * @return Date
     * @author TranLTH
     */
    public Date getExpiryDate() {
        return expiryDate;
    }

    /**
     * Set expiryDate
     * @param   expiryDate
     *          type Date
     * @return
     * @author  TranLTH
     */
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * Get url
     * @return String
     * @author TranLTH
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set url
     * @param   url
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get positionName
     * @return String
     * @author TranLTH
     */
    public String getPositionName() {
        return positionName;
    }

    /**
     * Set positionName
     * @param   positionName
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    /**
     * Get experience
     * @return String
     * @author TranLTH
     */
    public String getExperience() {
        return experience;
    }

    /**
     * Set experience
     * @param   experience
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setExperience(String experience) {
        this.experience = experience;
    }

    /**
     * Get location
     * @return String
     * @author TranLTH
     */
    public String getLocation() {
        return location;
    }

    /**
     * Set location
     * @param   location
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Get division
     * @return String
     * @author TranLTH
     */
    public String getDivision() {
        return division;
    }

    /**
     * Set division
     * @param   division
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Get status
     * @return String
     * @author TranLTH
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set status
     * @param   status
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Get listCareer
     * @return List<String>
     * @author TranLTH
     */
    public List<String> getListCareer() {
        return listCareer;
    }

    /**
     * Set listCareer
     * @param   listCareer
     *          type List<String>
     * @return
     * @author  TranLTH
     */
    public void setListCareer(List<String> listCareer) {
        this.listCareer = listCareer;
    }

    /**
     * Get listDivision
     * @return List<String>
     * @author TranLTH
     */
    public List<String> getListDivision() {
        return listDivision;
    }

    /**
     * Set listDivision
     * @param   listDivision
     *          type List<String>
     * @return
     * @author  TranLTH
     */
    public void setListDivision(List<String> listDivision) {
        this.listDivision = listDivision;
    }

    /**
     * Get listLocation
     * @return List<String>
     * @author TranLTH
     */
    public List<String> getListLocation() {
        return listLocation;
    }

    /**
     * Set listLocation
     * @param   listLocation
     *          type List<String>
     * @return
     * @author  TranLTH
     */
    public void setListLocation(List<String> listLocation) {
        this.listLocation = listLocation;
    }

    /**
     * Get jobTitle
     * @return String
     * @author TranLTH
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * Set jobTitle
     * @param   jobTitle
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * Get createBy
     * @return String
     * @author TranLTH
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * Set createBy
     * @param   createBy
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * Get expiryDateFrom
     * @return Date
     * @author TranLTH
     */
    public Date getExpiryDateFrom() {
        return expiryDateFrom;
    }

    /**
     * Set expiryDateFrom
     * @param   expiryDateFrom
     *          type Date
     * @return
     * @author  TranLTH
     */
    public void setExpiryDateFrom(Date expiryDateFrom) {
        this.expiryDateFrom = expiryDateFrom;
    }

    /**
     * Get expiryDateTo
     * @return Date
     * @author TranLTH
     */
    public Date getExpiryDateTo() {
        return expiryDateTo;
    }

    /**
     * Set expiryDateTo
     * @param   expiryDateTo
     *          type Date
     * @return
     * @author  TranLTH
     */
    public void setExpiryDateTo(Date expiryDateTo) {
        this.expiryDateTo = expiryDateTo;
    }
}