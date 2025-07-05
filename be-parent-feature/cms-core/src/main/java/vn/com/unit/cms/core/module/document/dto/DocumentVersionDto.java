/*******************************************************************************
 * Class        ：Document
 * Created date ：2017/04/18
 * Lasted date  ：2017/04/18
 * Author       ：TaiTM
 * Change log   ：2017/04/18：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.document.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * Document
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
@ToString
public class DocumentVersionDto extends AbstractTracking {
    private Long id;
    private Long documentId;
    private int version;
    private boolean currentVersion;
    private String physicalFileName;
    private String fileName;
    private Double fileSize;
    private String fileType;
    private String title;
    private String fullPhysicalFileName;
    private String categoryName;
}
