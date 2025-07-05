package vn.com.unit.cms.core.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;

@Getter
@Setter
public class CmsCommonSearchResultFilterDto {

    private Long id;

    private Long customerTypeId;

    private int forCandidate;

    private String categoryType;

    private String code; // Code

    private String title; // Name

    private String typeName; // Loại

    private String languageCode; // Language
    
    private String channel; //Channel

    private int enabled; // Active

    private String statusName; // Trạng thái của process: name
    private String statusCode; // Trạng thái của process: code

    private String createBy;
    private Date createDate;

    private String updateBy;
    private Date updateDate;

    private String deletedBy;
    private Date deletedDate;

    private int isVideo; // Có phải là video hay không. 0: false. 1: true.
    private int isVideoYoutube; // Có phải là video từ Youtube hay không. 0: false. 1: true.
    
    private int no; // Giành cho export Excel

    /**
     * Giành cho màn hình sắp xếp lại
     */
    private List<SortOrderDto> sortOderList;
    
    private int countUsed;
}
