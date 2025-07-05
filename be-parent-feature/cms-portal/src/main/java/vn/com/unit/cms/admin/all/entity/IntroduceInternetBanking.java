/*******************************************************************************
 * Class        ：IntroduceInternetBanking
 * Created date ：2017/08/23
 * Lasted date  ：2017/08/23
 * Author       ：hoangnp
 * Change log   ：2017/08/23：01-00 hoangnp create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * IntroduceInternetBanking
 * 
 * @version 01-00
 * @since 01-00
 * @author hoangnp
 */
@Table(name="m_introduce_internet_banking")
public class IntroduceInternetBanking extends AbstractTracking  {
     
	 @Id
	 @Column(name = "id")
	 @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_INTRODUCE_INTERNET_BANKING")
	 private Long id;
	 
	 @Column(name="code")
	 private String code;
	 
	 @Column(name="name")
	 private String name;
	 
	 @Column(name = "enabled")
	 private boolean enabled;
	 
	 @Column(name = "introduction_type")
	 private String introductionType;
	 
	/**
	 * getId
	 *
	 * @return Long
	 * @author hoangnp
	 */
	public Long getId() {
		return id;
	}

	/**
	 * setId
	 *
	 * @param id
	 * @author hoangnp
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * getCode
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getCode() {
		return code;
	}

	/**
	 * setCode
	 *
	 * @param code
	 * @author hoangnp
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * getName
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getName() {
		return name;
	}

	/**
	 * setName
	 *
	 * @param name
	 * @author hoangnp
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * isEnabled
	 *
	 * @return boolean
	 * @author hoangnp
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * setEnabled
	 *
	 * @param enabled
	 * @author hoangnp
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getIntroductionType() {
		return introductionType;
	}

	public void setIntroductionType(String introductionType) {
		this.introductionType = introductionType;
	}
	 
	 
}
