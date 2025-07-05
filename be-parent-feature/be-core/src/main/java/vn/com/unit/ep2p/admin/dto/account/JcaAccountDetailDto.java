package vn.com.unit.ep2p.admin.dto.account;

import java.util.Date;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.dto.JcaRoleForAccountDto;
import vn.com.unit.ep2p.admin.dto.JcaAbstractCompanyDto;
@Getter
@Setter
public class JcaAccountDetailDto extends JcaAbstractCompanyDto {

    private Long id;
    
    private String username;
    
    private String fullname;
    
    private String email;
    
    private Date birthday;
    
    private String phone;
    
    private List<Long> groupId;
    
    private String cmnd;
    
    private Boolean enabled;
    
    private String statusCode;
    
    private String avatar;
    
    private String departmentName;
    
    private String positionName;
    
    private String branchName;
    
    private String channel;
    
    private String partner;
    
    private List<JcaRoleForAccountDto> listRoleForAccount;
    
    private String url;
    
    private boolean locked;
    private boolean actived;
    
    private MultipartFile avatarFile;
    private Long repositoryId;
    
    private String code;
    private String gender;
    
    private boolean pushNotification;
    private boolean pushEmail;  
    private boolean archiveFlag;
    
    
    
}
