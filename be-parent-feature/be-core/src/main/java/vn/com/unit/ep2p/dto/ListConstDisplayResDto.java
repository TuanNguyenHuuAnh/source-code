package vn.com.unit.ep2p.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.res.ResAPI;

@Getter
@Setter
public class ListConstDisplayResDto extends ResAPI{
	private List<Select2Dto> resultObj;
}
