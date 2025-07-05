/*******************************************************************************
 * Class        ：NewsLanguage
 * Created date ：2017/02/24
 * Lasted date  ：2017/02/24
 * Author       ：hand
 * Change log   ：2017/02/24：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * InterestRateLanguage
 * 
 * @version 01-00
 * @since 01-00
 * @author sonnt
 */
@Table(name = "m_interest_rate_language")
public class InterestRateLanguage extends AbstractTracking {

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_INTEREST_RATE_LANGUAGE")
    private Long id;

    @Column(name = "interest_rate_id")
    private Long interestRateId;

    @Column(name = "m_language_code")
    private String languageCode;
    
    @Column(name = "description")
    private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getInterestRateId() {
		return interestRateId;
	}

	public void setInterestRateId(Long interestRateId) {
		this.interestRateId = interestRateId;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
