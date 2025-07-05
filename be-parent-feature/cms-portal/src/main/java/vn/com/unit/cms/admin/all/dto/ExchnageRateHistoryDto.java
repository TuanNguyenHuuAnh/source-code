/*******************************************************************************
 * Class        ：ExchnageRateHistoryDto
 * Created date ：2017/04/19
 * Lasted date  ：2017/04/26
 * Author       ：phunghn
 * Change log   ：2017/04/26：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;

/**
* ExchnageRateHistoryDto
* 
* @version 01-00
* @since 01-00
* @author phunghn
*/
public class ExchnageRateHistoryDto {
    /** id */
	private Long id;
	/** mRxchangeRateId */
	private Long mRxchangeRateId;
	/** mCurrencyId */
	private Long mCurrencyId;
	/** mCurrencyName */
	private String mCurrencyName;
	/** updateDateTime */
	private Date updateDateTime;
	/** buying */
	private String buying;
	/** tranfers */
	private String tranfers;
	/** selling */
	private String selling;
	/** createDate */
	private Date createDate;
    /**
     * Get id
     * @return Long
     * @author phunghn
     */
    public Long getId() {
        return id;
    }
    /**
     * Set id
     * @param   id
     *          type Long
     * @return
     * @author  phunghn
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Get mRxchangeRateId
     * @return Long
     * @author phunghn
     */
    public Long getmRxchangeRateId() {
        return mRxchangeRateId;
    }
    /**
     * Set mRxchangeRateId
     * @param   mRxchangeRateId
     *          type Long
     * @return
     * @author  phunghn
     */
    public void setmRxchangeRateId(Long mRxchangeRateId) {
        this.mRxchangeRateId = mRxchangeRateId;
    }
    /**
     * Get mCurrencyId
     * @return Long
     * @author phunghn
     */
    public Long getmCurrencyId() {
        return mCurrencyId;
    }
    /**
     * Set mCurrencyId
     * @param   mCurrencyId
     *          type Long
     * @return
     * @author  phunghn
     */
    public void setmCurrencyId(Long mCurrencyId) {
        this.mCurrencyId = mCurrencyId;
    }
    /**
     * Get mCurrencyName
     * @return String
     * @author phunghn
     */
    public String getmCurrencyName() {
        return mCurrencyName;
    }
    /**
     * Set mCurrencyName
     * @param   mCurrencyName
     *          type String
     * @return
     * @author  phunghn
     */
    public void setmCurrencyName(String mCurrencyName) {
        this.mCurrencyName = mCurrencyName;
    }
    /**
     * Get updateDateTime
     * @return Date
     * @author phunghn
     */
    public Date getUpdateDateTime() {
        return updateDateTime;
    }
    /**
     * Set updateDateTime
     * @param   updateDateTime
     *          type Date
     * @return
     * @author  phunghn
     */
    public void setUpdateDateTime(Date updateDateTime) {
        this.updateDateTime = updateDateTime;
    }
    /**
     * Get buying
     * @return String
     * @author phunghn
     */
    public String getBuying() {
        return buying;
    }
    /**
     * Set buying
     * @param   buying
     *          type String
     * @return
     * @author  phunghn
     */
    public void setBuying(String buying) {
        this.buying = buying;
    }
    /**
     * Get tranfers
     * @return String
     * @author phunghn
     */
    public String getTranfers() {
        return tranfers;
    }
    /**
     * Set tranfers
     * @param   tranfers
     *          type String
     * @return
     * @author  phunghn
     */
    public void setTranfers(String tranfers) {
        this.tranfers = tranfers;
    }
    /**
     * Get selling
     * @return String
     * @author phunghn
     */
    public String getSelling() {
        return selling;
    }
    /**
     * Set selling
     * @param   selling
     *          type String
     * @return
     * @author  phunghn
     */
    public void setSelling(String selling) {
        this.selling = selling;
    }
    /**
     * Get createDate
     * @return Date
     * @author phunghn
     */
    public Date getCreateDate() {
        return createDate;
    }
    /**
     * Set createDate
     * @param   createDate
     *          type Date
     * @return
     * @author  phunghn
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }	
	
}
