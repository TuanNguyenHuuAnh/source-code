package vn.com.unit.cms.core.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;

/**
 * @author TaiTM
 */
@Getter
@Setter
public class CmsCommonEditDto extends DocumentActionReq {
    private Long id;
    private Long customerTypeId;
    private String code;
    private String title;
    private Long docId;
    private String note;

    private boolean enabled;
    
    private Integer indexLangActive = new Integer(0);

    private Long sort;

    private String customerAlias;
    
    private String statusCode;
    private String statusName;

    private String searchDto;
    
    private Date postedDate;
    private String postedDateString;

    private Date expirationDate;
    private String expirationDateString;

    private String createBy;
    private Date createDate;

    private String updateBy;
    private Date updateDate;

    private String url;

    private Boolean hasEdit = true;

    private String requestToken;
    private String referenceType;
    private Long referenceId;
}
