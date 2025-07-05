/*******************************************************************************
 * Class        ：FileRepositoryDto
 * Created date ：2020/07/21
 * Lasted date  ：2020/07/21
 * Author       ：tantm
 * Change log   ：2020/07/21：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.storage.dto;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.common.dto.AbstractCompanyDto;

/**
 * 
 * FileRepositoryDto
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Getter
@Setter
public class JcaRepositoryDto extends AbstractCompanyDto {

    private Long id;

    private String code;

    private String name;

    private String physicalPath;

//    private Date durationStart;
//
//    private Date durationEnd;

    private String subFolderRule;

    private Boolean actived = false;

    private String typeRepo;
    
    private String typeRepoName;

    private String description;

    private Long companyId;

    private Long fileProtocol;
    
    private String fileProtocolName;

    private String site;

    private String path;

    private String note;
    
    private String username;

    private String password;

    private String host;
    
    private String url;
    
    private String status;
    
    private Integer no;
    private String companyName;

    /**
     * Get host info
     * 
     * @return Host as string
     * @author tantm
     */
    public String getHost() {
        String tmpHost = host;
        if (StringUtils.isBlank(tmpHost)) {
            tmpHost = physicalPath;
        }
        return tmpHost;
    }
}
