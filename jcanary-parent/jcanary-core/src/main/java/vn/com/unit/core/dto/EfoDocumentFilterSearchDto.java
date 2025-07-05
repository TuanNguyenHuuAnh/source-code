/*******************************************************************************
 * Class        ：EfoDocumentFilterSearchDto
 * Created date ：2021/01/21
 * Lasted date  ：2021/01/21
 * Author       ：taitt
 * Change log   ：2021/01/21：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * EfoDocumentFilterSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class EfoDocumentFilterSearchDto {

    private List<String> statusCommons;
    private String stautusCode;

    private Long companyId;
    private Long orgId;
    private Long formId;
    private String priority;

    private Date fromDate;
    private Date toDate;

    // filter search
    private String docTitle;
    private String docCode;
    private String submittedName;

    private String channel;
    private String actionUser;
    private Long actionUserId;
    
    private String lang;
    
    private int hasViewAll;
    private int hasViewHightLevel;
}
