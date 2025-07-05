/*******************************************************************************
 * Class        ：JcaCaManagementDto
 * Created date ：2020/12/16
 * Lasted date  ：2020/12/16
 * Author       ：minhnv
 * Change log   ：2020/12/16：01-00 minhnv create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JcaCaManagementDto
 * 
 * @version 01-00
 * @since 01-00
 * @author minhnv
 */
@Setter
@Getter
@NoArgsConstructor
public class JcaCaManagementDto extends AbstractTracking {

    private Long caManagementId;
    private Long accountId;
    private String caSlot;
    private String caPassword;
    private String caName;
    private boolean caDefault;
    private Long companyId;
    private String accountName;
    private String caLabel;
    private String caSerial;
    private int storeType;
    
    private String companyName;
}
