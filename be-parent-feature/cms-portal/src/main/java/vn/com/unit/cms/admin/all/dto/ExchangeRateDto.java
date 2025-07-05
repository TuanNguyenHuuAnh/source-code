/*******************************************************************************
 * Class        ：ExchangeRateDto
 * Created date ：2017/04/19
 * Lasted date  ：2017/04/26
 * Author       ：phunghn
 * Change log   ：2017/04/26：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;

/**
* ExchangeRateDto
* 
* @version 01-00
* @since 01-00
* @author phunghn
*/
public class ExchangeRateDto {
    /** id */
	private Long id;
	/** mCurrencyId */
	private Long mCurrencyId;
	/** mCurrencyName */
	private String mCurrencyName;
	/** displayDate */
	private Date displayDate;
	/** buying */
	private String buying;
	/** transfer */
	private String transfer;
	/** selling */
	private String selling;
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
     * Get displayDate
     * @return Date
     * @author phunghn
     */
    public Date getDisplayDate() {
        return displayDate;
    }
    /**
     * Set displayDate
     * @param   displayDate
     *          type Date
     * @return
     * @author  phunghn
     */
    public void setDisplayDate(Date displayDate) {
        this.displayDate = displayDate;
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
     * Get transfer
     * @return String
     * @author phunghn
     */
    public String getTransfer() {
        return transfer;
    }
    /**
     * Set transfer
     * @param   transfer
     *          type String
     * @return
     * @author  phunghn
     */
    public void setTransfer(String transfer) {
        this.transfer = transfer;
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
	
	
}
