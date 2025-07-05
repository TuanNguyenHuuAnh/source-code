package vn.com.unit.ep2p.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.dto.ConditionSearchCommonDto;

import java.util.List;

@Getter
@Setter
public class PopupSearchReqDto {
    private Long companyId;
    private String name;
    private String create;
    private String code;
}
