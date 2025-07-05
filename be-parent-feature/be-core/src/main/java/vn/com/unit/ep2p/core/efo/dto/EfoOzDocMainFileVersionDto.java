/*******************************************************************************
 * Class        ：EfoOzDocMainFileVersionDto
 * Created date ：2019/08/09
 * Lasted date  ：2019/08/09
 * Author       ：NhanNV
 * Change log   ：2019/08/09：01-00 NhanNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * EfoOzDocMainFileVersionDto
 * 
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */
@Getter
@Setter
public class EfoOzDocMainFileVersionDto {

    private Long id;

    private Long majorVersion;

    private Long minorVersion;

    private String mainFileName;

    private String mainFilePath;

    private Long mainFileRepoId;

    private String mainFileNameView;

    private String pdfFilePath;

    private Long pdfMajorVersion;

    private Long pdfMinorVersion;
    
    private Long pdfRepoI;

    private String validJson;

    /** view */
    private String actionName;
    private String createdBy;

}
