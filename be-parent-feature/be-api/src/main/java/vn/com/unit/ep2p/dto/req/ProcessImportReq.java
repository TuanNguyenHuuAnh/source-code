/*******************************************************************************
 * Class        ：ProcessImportReq
 * Created date ：2021/01/12
 * Lasted date  ：2021/01/12
 * Author       ：KhuongTH
 * Change log   ：2021/01/12：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import lombok.Getter;
import lombok.Setter;

/**
 * <p> ProcessImportReq </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Getter
@Setter
public class ProcessImportReq {
    private Long companyId;
    private boolean override;
    private String importFile;
}
