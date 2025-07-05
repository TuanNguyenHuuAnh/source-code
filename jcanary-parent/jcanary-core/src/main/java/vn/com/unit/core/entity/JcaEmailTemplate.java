/*******************************************************************************
 * Class        :JcaEmailTemplate
 * Created date :2020/12/23
 * Lasted date  :2020/12/23
 * Author       :SonND
 * Change log   :2020/12/23:01-00 SonND create a new
 ******************************************************************************/

package vn.com.unit.core.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.db.entity.AbstractTracking;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = CoreConstant.TABLE_JCA_EMAIL_TEMPLATE)
public class JcaEmailTemplate extends AbstractTracking {

	@Id
	@Column(name = "ID")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CoreConstant.SEQ +  CoreConstant.TABLE_JCA_EMAIL_TEMPLATE)
	private Long id;

    @Column(name = "NAME")
    private String name;
    
    @Column(name = "CODE")
    private String code;
    
    @Column(name = "TEMPLATE_CONTENT")
    private String templateContent;
    
    @Column(name = "TEMPLATE_SUBJECT")
    private String templateSubject;
    
    @Column(name = "COMPANY_ID")
    private Long companyId;
}