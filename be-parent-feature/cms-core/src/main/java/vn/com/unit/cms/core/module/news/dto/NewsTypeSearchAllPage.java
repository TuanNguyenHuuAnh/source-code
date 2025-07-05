package vn.com.unit.cms.core.module.news.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * NewsTypeSearchAllPage
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Getter
@Setter
public class NewsTypeSearchAllPage {
    private Long id;

    private Long newsTypeId;
    
    private String code;
    
    private String title;
    
    private String label;
    private String linkAlias;
    private Integer total;
}
