package vn.com.unit.cms.core.module.result.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class ResultSearchDto extends CommonSearchWithPagingDto {
	private String agentCode;
	private String agentTitle;//chuc danh
    private String yyyyMM;

    private String agentGroup;
    private String  orgCode;
    private Object territoryName;
    private Object areaName;
    private Object regionName;
    private Object office;
    private Object bdth;
    private Object bdah;
    private Object bdrh;
    private Object bdoh;
    private Object agent;
    private Object mannger;

}
