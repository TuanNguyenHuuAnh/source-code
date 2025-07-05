/*******************************************************************************
 * Class        ：ReqSaveOzdFile
 * Created date ：2019/11/05
 * Lasted date  ：2019/11/05
 * Author       ：HungHT
 * Change log   ：2019/11/05：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.core.req;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * ReqSaveOzdFile
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Getter
@Setter
public class ReqSaveOzdFile {

    /** file ozd */
    private List<String> fileStream;
    private List<String> fileStreamName;

    private Long id;
    private Long formId;
    private String systemCode;
    private String createdDate;
    private String fileName;
    private String formFileName;
    
    private String ozUser;
    private String ozPassword;
    private String ozServerUrl;
    
    private String webDavUrl;
    private String webDavUser;
    private String webDavPassword;
}
