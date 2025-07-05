package vn.com.unit.cms.core.module.information.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;
import lombok.AllArgsConstructor;
@AllArgsConstructor
public class GAInformationParam {
    @In
    public String orgCode;
	@In
	public String agentCode;
	@ResultSet
	public List<GAInformationDto> data;
}
