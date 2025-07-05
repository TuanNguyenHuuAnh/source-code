package vn.com.unit.ep2p.admin.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.ResultSet;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class NotifyBaseParamDto {
	
	@ResultSet
    public List<NotifyBaseDto> lstData;
}
