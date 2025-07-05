/*******************************************************************************
 * Class        ：InvoiceInformationDto
 * Created date ：2019/06/04
 * Lasted date  ：2019/06/04
 * Author       ：hand
 * Change log   ：2019/06/04：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * InvoiceInformationDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public class InvoiceInformationDto {
    
    /** Tax Code */
    private String taxCode;
    
    /** Invoice Type */
    private String invoiceType;
    
    /** Invoice Serial */
    private String invoiceSerial;

    /** Invoice Date */
    private Date invoiceDate;

    /** Invoice Number */
    private String invoiceNumber;

    /** Description */
    private String description;
    
    private String descriptionVi;

    /** Paid Amount */
    private BigDecimal paidAmount;

    /** Invoice Amount */
    private BigDecimal invoiceAmount;

    /** Invoice Adjustment */
    private BigDecimal invoiceAdjust;

    /** Payable Amount */
    private BigDecimal payableAmount;

    /** Amount (VND) */
    private BigDecimal amount;
    
    /** INVOICE_GENERATE */
    private String invoiceGenerate;
    
    private Long id;
    
    private Long paymentID;
    
    private String paymentNo;
    
    /** PO_NO */
    private String poNo;

    /** PO_TOTAL_AMOUNT */
    private BigDecimal poTotalAmount;
    
    private String currency;
    
    private String accountType;
    
    private Double rate;
    
    private BigDecimal invoiceAmountVnd;
    
    private String payeeCode;
    
    private String tCode3;
    
    private String tCode4;

    private String tCode6;

    /**
     * Get invoiceType
     * @return String
     * @author hand
     */
    public String getInvoiceType() {
        return invoiceType;
    }

    /**
     * Set invoiceType
     * @param   invoiceType
     *          type String
     * @return
     * @author  hand
     */
    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    /**
     * Get invoiceDate
     * @return Date
     * @author hand
     */
    public Date getInvoiceDate() {
        return invoiceDate;
    }

    /**
     * Set invoiceDate
     * @param   invoiceDate
     *          type Date
     * @return
     * @author  hand
     */
    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    /**
     * Get invoiceNumber
     * @return String
     * @author hand
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * Set invoiceNumber
     * @param   invoiceNumber
     *          type String
     * @return
     * @author  hand
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    /**
     * Get description
     * @return String
     * @author hand
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description
     * @param   description
     *          type String
     * @return
     * @author  hand
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get paidAmount
     * @return BigDecimal
     * @author hand
     */
    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    /**
     * Set paidAmount
     * @param   paidAmount
     *          type BigDecimal
     * @return
     * @author  hand
     */
    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    /**
     * Get invoiceAmount
     * @return BigDecimal
     * @author hand
     */
    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    /**
     * Set invoiceAmount
     * @param   invoiceAmount
     *          type BigDecimal
     * @return
     * @author  hand
     */
    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    /**
     * Get invoiceAdjust
     * @return BigDecimal
     * @author hand
     */
    public BigDecimal getInvoiceAdjust() {
        return invoiceAdjust;
    }

    /**
     * Set invoiceAdjust
     * @param   invoiceAdjust
     *          type BigDecimal
     * @return
     * @author  hand
     */
    public void setInvoiceAdjust(BigDecimal invoiceAdjust) {
        this.invoiceAdjust = invoiceAdjust;
    }

    /**
     * Get payableAmount
     * @return BigDecimal
     * @author hand
     */
    public BigDecimal getPayableAmount() {
        return payableAmount;
    }

    /**
     * Set payableAmount
     * @param   payableAmount
     *          type BigDecimal
     * @return
     * @author  hand
     */
    public void setPayableAmount(BigDecimal payableAmount) {
        this.payableAmount = payableAmount;
    }

    /**
     * Get amount
     * @return BigDecimal
     * @author hand
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Set amount
     * @param   amount
     *          type BigDecimal
     * @return
     * @author  hand
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Get invoiceGenerate
     * @return String
     * @author hand
     */
    public String getInvoiceGenerate() {
        return invoiceGenerate;
    }

    /**
     * Set invoiceGenerate
     * @param   invoiceGenerate
     *          type String
     * @return
     * @author  hand
     */
    public void setInvoiceGenerate(String invoiceGenerate) {
        this.invoiceGenerate = invoiceGenerate;
    }

    /**
     * Get id
     * @return Long
     * @author hand
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     * @param   id
     *          type Long
     * @return
     * @author  hand
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get paymentID
     * @return Long
     * @author hand
     */
    public Long getPaymentID() {
        return paymentID;
    }

    /**
     * Set paymentID
     * @param   paymentID
     *          type Long
     * @return
     * @author  hand
     */
    public void setPaymentID(Long paymentID) {
        this.paymentID = paymentID;
    }

    /**
     * Get paymentNo
     * @return String
     * @author hand
     */
    public String getPaymentNo() {
        return paymentNo;
    }

    /**
     * Set paymentNo
     * @param   paymentNo
     *          type String
     * @return
     * @author  hand
     */
    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    /**
     * Get poNo
     * @return String
     * @author hand
     */
    public String getPoNo() {
        return poNo;
    }

    /**
     * Set poNo
     * @param   poNo
     *          type String
     * @return
     * @author  hand
     */
    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    /**
     * Get poTotalAmount
     * @return BigDecimal
     * @author hand
     */
    public BigDecimal getPoTotalAmount() {
        return poTotalAmount;
    }

    /**
     * Set poTotalAmount
     * @param   poTotalAmount
     *          type BigDecimal
     * @return
     * @author  hand
     */
    public void setPoTotalAmount(BigDecimal poTotalAmount) {
        this.poTotalAmount = poTotalAmount;
    }

    /**
     * @return the currency
     * @author taitm
     * @date   Jul 19, 2019
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param  currency the currency to set
     * @author taitm
     * @date   Jul 19, 2019
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

	public String getInvoiceSerial() {
		return invoiceSerial;
	}

	public void setInvoiceSerial(String invoiceSerial) {
		this.invoiceSerial = invoiceSerial;
	}

    /**
     * @return the taxCode
     * @author taitm
     * @date   Aug 26, 2019
     */
    public String getTaxCode() {
        return taxCode;
    }

    /**
     * @param  taxCode the taxCode to set
     * @author taitm
     * @date   Aug 26, 2019
     */
    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

	/**
	 * @return the accountType
	 * @author tritv
	 */
	public String getAccountType() {
		return accountType;
	}

	/**
	 * @param accountType the accountType to set
	 * @author tritv
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * @return the rate
	 * @author tritv
	 */
	public Double getRate() {
		return rate;
	}

	/**
	 * @param rate the rate to set
	 * @author tritv
	 */
	public void setRate(Double rate) {
		this.rate = rate;
	}

	/**
	 * @return the invoiceAmountVnd
	 * @author tritv
	 */
	public BigDecimal getInvoiceAmountVnd() {
		return invoiceAmountVnd;
	}

	/**
	 * @param invoiceAmountVnd the invoiceAmountVnd to set
	 * @author tritv
	 */
	public void setInvoiceAmountVnd(BigDecimal invoiceAmountVnd) {
		this.invoiceAmountVnd = invoiceAmountVnd;
	}

    /**
     * Get payeeCode
     * @return String
     * @author hand
     */
    public String getPayeeCode() {
        return payeeCode;
    }

    /**
     * Set payeeCode
     * @param   payeeCode
     *          type String
     * @return
     * @author  hand
     */
    public void setPayeeCode(String payeeCode) {
        this.payeeCode = payeeCode;
    }

    /**
     * @return the descriptionVi
     * @author taitm
     * @date   Nov 8, 2019
     */
    public String getDescriptionVi() {
        return descriptionVi;
    }

    /**
     * @param  descriptionVi the descriptionVi to set
     * @author taitm
     * @date   Nov 8, 2019
     */
    public void setDescriptionVi(String descriptionVi) {
        this.descriptionVi = descriptionVi;
    }

	public String gettCode3() {
		return tCode3;
	}

	public void settCode3(String tCode3) {
		this.tCode3 = tCode3;
	}

	public String gettCode4() {
		return tCode4;
	}

	public void settCode4(String tCode4) {
		this.tCode4 = tCode4;
	}

	/**
	 * @author Vunt
	 * @return the tCode6
	 */
	public String gettCode6() {
		return tCode6;
	}

	/**
	 * @author Vunt
	 * @param tCode6 the tCode6 to set
	 */
	public void settCode6(String tCode6) {
		this.tCode6 = tCode6;
	}
	
}
