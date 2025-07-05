/*******************************************************************************
 * Class        JcaAccountEditDto
 * Created date 2016/07/21
 * Lasted date  2016/07/21
 * Author       KhoaNA
 * Change log   2016/07/2101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.dto.account;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.dto.JcaAccountOrgDto;
import vn.com.unit.core.dto.JcaRoleForAccountDto;
import vn.com.unit.ep2p.admin.dto.JcaAbstractCompanyDto;

@Getter
@Setter
public class JcaAccountEditDto extends JcaAbstractCompanyDto {

    private Long id;
    
    private String username;
    
    @Size(min=1,max=255)
    private String fullname;
    
    @Size(min=1,max=255)
    private String email;
    
    private Date birthday;
    
    @Size(min=0,max=200)
    private String phone;
    
    private Boolean enabled;
    
    private String avatar;
    
    private Long departmentId;
    
    private Long positionId;
    
    private Long branchId;
    
    private String channel;
    
    private List<String> channelList;
    
    private String partner;
    
    private List<String> partnerList;
    
    private List<JcaRoleForAccountDto> listRoleForAccount;
    
    private List<JcaAccountOrgDto> listOrgForAccount;
    
    private String url;
    
    private String description;
    
    private boolean locked;
    private boolean actived;
    
    private MultipartFile avatarFile;
    private Long repositoryId;   
    private List<Long> groupId;
    
    private String code;
    private String gender;
    private Long apiFlag;
    private String updatedBy;
    
    private boolean pushNotification;
    private boolean pushEmail;
    
    private String positionName;
    
    private String cmnd;
    
    private boolean localAccount;
    private String password;
    
    private boolean archiveFlag;
    
    private boolean canSendHO;
    private boolean isHO;
    
    private String imgTemp;
    
}
