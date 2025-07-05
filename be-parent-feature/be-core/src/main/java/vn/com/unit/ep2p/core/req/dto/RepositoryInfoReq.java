/*******************************************************************************
 * Class        ：RepositoryInfoReq
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：tantm
 * Change log   ：2020/12/22：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * RepositoryInfoReq
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Getter
@Setter
public class RepositoryInfoReq {

    @ApiModelProperty(notes = "Code of the repository", example = "REPO_MAIN", required = true, position = 0)
    private String code;

    @ApiModelProperty(notes = "Name of the repository", example = "Main folder", required = true, position = 0)
    private String name;

    @ApiModelProperty(notes = "Duration start of the repository", example = "20201212", required = false, position = 0)
    private Date durationStart;

    @ApiModelProperty(notes = "Duration end of the repository", example = "20201212", required = false, position = 0)
    private Date durationEnd;

    @ApiModelProperty(notes = "Code of the repository", example = "//192.168.1.118/data_share", required = true, position = 0)
    private String physicalPath;

    @ApiModelProperty(notes = "Subfolder rule of the repository", example = "yyyy/MM/dd", required = false, position = 0)
    private String subFolderRule;

    @ApiModelProperty(notes = "Active of the repository", example = "1", required = true, position = 0)
    private Boolean active = false;

    @ApiModelProperty(notes = "Type of the repository", example = "1", required = true, position = 0)
    private String typeRepo;

    @ApiModelProperty(notes = "Sescription of the repository", example = "Main folder", required = false, position = 0)
    private String description;

    @ApiModelProperty(notes = "Company id of the repository", example = "1", required = true, position = 0)
    private Long companyId;

    @ApiModelProperty(notes = "File protocol of the repository", example = "1", required = true, position = 0)
    private Long fileProtocol;

    @ApiModelProperty(notes = "Site of the repository", example = "hdbank", required = false, position = 0)
    private String site;

    @ApiModelProperty(notes = "Path of the repository", example = "hdbank", required = false, position = 0)
    private String path;

    @ApiModelProperty(notes = "User name of the repository", example = "admin", required = false, position = 0)
    private String username;

    @ApiModelProperty(notes = "Password of the repository", example = "123", required = false, position = 0)
    private String password;
}
