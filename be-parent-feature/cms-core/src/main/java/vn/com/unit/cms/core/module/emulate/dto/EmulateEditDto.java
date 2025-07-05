/*******************************************************************************
 * Class        ：EmulateEditDto
 * Created date ：2017/03/19
 * Lasted date  ：2017/03/19
 * Author       ：TaiTM
 * Change log   ：2017/03/19：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.emulate.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonEditDto;

/**
 * FaqsEditDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
public class EmulateEditDto extends CmsCommonEditDto {
    private String memoCode;

    List<EmulateLanguageDto> emulateLanguageDto;

    private boolean hot;
    private Date startDate;
    private Date endDate;
    private int type;
    private String typeName;

    private int subjectsApplicable;
    private String territory;
    private List<String> territoryList;
    private String area;
    private List<String> areaList;
    private String region;
    private List<String> regionList;
    private String office;
    private List<String> officeList;
    private String agentType;
    private List<String> agentTypeList;
    private boolean fc;
    
    private boolean linkDms;
    private String channel;
    private List<String> channelList;
}