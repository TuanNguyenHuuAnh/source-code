/*******************************************************************************
 * Class        :JcaConstantDto
 * Created date :2020/12/01
 * Lasted date  :2020/12/01
 * Author       :tantm
 * Change log   :2020/12/01:01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * JcaConstantDto
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JcaConstantDto {
    
    private Long id;
    private Long groupId;
    private String groupCode;
    private String kind;
    private String code;
    private Long displayOrder;
    private Long langId;
    private String langCode;
    private String name;
    private String nameEn;
    private String nameVi;
    private String langCodeEn = "EN";
    private String langCodeVi = "VI";
    private Long langIdEn;
    private Long langIdVi;
    private Integer actived;
    private boolean canDelete;
    private String oldKind;
    private String oldCode;
    private String oldGroupCode;

    /** @param id2
    /** @param code2
     * @author ngannh
     */
    public JcaConstantDto(Long id2, String code2) {
        this.id = id2;
        this.code = code2;
    }

}