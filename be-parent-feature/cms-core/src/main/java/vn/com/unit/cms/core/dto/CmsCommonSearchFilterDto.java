package vn.com.unit.cms.core.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.dto.ConditionSearchCommonDto;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;

/**
 * @author TaiTM
 * @description: Sử dụng các biến kiểu String để tạo chuỗi phục vụ cho việc
 *               search theo điều kiện filter
 */
@Getter
@Setter
public class CmsCommonSearchFilterDto extends ConditionSearchCommonDto {

    private static final long serialVersionUID = 2211939909468401672L;

    private Long id;

    private Long customerTypeId;
    
    private String functionCode;

    private String fieldSearch;

    private String code; // Code

    private String title; // Name

    private String languageCode; // Language

    private String enabled; // Active
    
    private String channel; // Channel

    private String username;
    
    private String statusName; // process status name
    private String statusCode; // process status code
    
    private String querySearch;
    
    private String createBy;
    private String createDate;

    private String expirationDate;
    private String postedDate;

    private String updateBy;
    private String updateDate;
    
    /**
     * - searchType = 0: Tìm kiếm theo điều kiện OR
     * - searchType = 1: Tìm kiếm theo điều kiện AND
     */
    private int searchType;

    private String url;
    
    private String token;
    
    private Long companyId;

    /**
     * Truyền data để cập nhật lại danh sách ở màn hình sắp xếp lại
     */
    private List<SortOrderDto> sortOderList;
    
    private List<String> listCheckInFilterTable;
}
