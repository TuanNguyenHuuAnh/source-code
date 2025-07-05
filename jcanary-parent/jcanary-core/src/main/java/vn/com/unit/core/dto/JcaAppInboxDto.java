/*******************************************************************************
 * Class        ：JcaAppInboxDto
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：TrieuVD
 * Change log   ：2021/02/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JcaAppInboxDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
public class JcaAppInboxDto extends AbstractTracking {
    private Long appInboxId;
    private Long userId;
    private String title;
    private String description;
    private boolean readFlag;
    private String data;
    private String responseJson;
}
