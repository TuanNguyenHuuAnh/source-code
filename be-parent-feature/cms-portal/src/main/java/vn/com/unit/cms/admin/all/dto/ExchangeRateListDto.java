/*******************************************************************************
 * Class        ：ExchangeRateListDto
 * Created date ：2017/04/19
 * Lasted date  ：2017/04/26
 * Author       ：phunghn
 * Change log   ：2017/04/26：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.List;

/**
 * ExchangeRateListDto
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public class ExchangeRateListDto {
    /** exchangeRateId */
    private Long exchangeRateId;
    /** List<ExchangeRateDto> */
    private List<ExchangeRateDto> data;
    /** url */
    private String url;
    /** langCode */
    private String langCode;
    /** DisplayDateIM */
    private Date DisplayDateIM;

    /**
     * Get exchangeRateId
     * 
     * @return Long
     * @author phunghn
     */
    public Long getExchangeRateId() {
        return exchangeRateId;
    }

    /**
     * Set exchangeRateId
     * 
     * @param exchangeRateId
     *            type Long
     * @return
     * @author phunghn
     */
    public void setExchangeRateId(Long exchangeRateId) {
        this.exchangeRateId = exchangeRateId;
    }

    /**
     * Get data
     * 
     * @return List<ExchangeRateDto>
     * @author phunghn
     */
    public List<ExchangeRateDto> getData() {
        return data;
    }

    /**
     * Set data
     * 
     * @param data
     *            type List<ExchangeRateDto>
     * @return
     * @author phunghn
     */
    public void setData(List<ExchangeRateDto> data) {
        this.data = data;
    }

    /**
     * Get url
     * 
     * @return String
     * @author phunghn
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set url
     * 
     * @param url
     *            type String
     * @return
     * @author phunghn
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get langCode
     * 
     * @return String
     * @author phunghn
     */
    public String getLangCode() {
        return langCode;
    }

    /**
     * Set langCode
     * 
     * @param langCode
     *            type String
     * @return
     * @author phunghn
     */
    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    /**
     * Get DisplayDateIM
     * 
     * @return Date
     * @author phunghn
     */
    public Date getDisplayDateIM() {
        return DisplayDateIM;
    }

    /**
     * Set DisplayDateIM
     * 
     * @param displayDateIM
     *            type Date
     * @return
     * @author phunghn
     */
    public void setDisplayDateIM(Date displayDateIM) {
        DisplayDateIM = displayDateIM;
    }

}
