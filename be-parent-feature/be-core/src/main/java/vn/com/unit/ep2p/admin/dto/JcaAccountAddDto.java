package vn.com.unit.ep2p.admin.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JcaAccountAddDto extends JcaAbstractCompanyDto{

    /** accountId */
    private Long id;
    
    /** username */
    @Size(min=4,max=100)
    private String username;
    
    /** password */
    private String password;
    
    /** fullname */
    @Size(min=1,max=255)
    private String fullname;
    
    /** email */
    @Size(min=1,max=255)
    private String email;
    
    /** birthday */
    private Date birthday;
    
    /** phone */
    @Size(min=0,max=200)
    private String phone;
    
    /** enabled */
    private Boolean enabled;
    
    /** avatar */
    private String avatar;
    
    /** departmentId */
    private Long departmentId;
    
    /** positionId */
    private Long positionId;
    
    /** branchId */
    private Long branchId;
    
    /** channel */
    private String channel;
    
    private List<String> channelList;
    
    /** partner */
    private String partner;
    
    private List<String> partnerList;
    
    private String url;
    
    private String description;
    
    private boolean locked;
    
    private MultipartFile avatarFile;
    private Long repositoryId;
    
    /** groupId */
    private List<Long> groupId;
    
    private String code;
    
    private String gender;
    
    private Long apiFlag;
    
    private String createBy;
    
    private boolean pushNotification;
    
    private boolean pushEmail;
    
    private String cmnd;
    
    private boolean archiveFlag;
    private String accountType;
    
}
