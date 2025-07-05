package vn.com.unit.ep2p.core.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.common.dto.Select2Dto;

/**
 * @author TaiTM
 **/
@Getter
@Setter
public class CommonSearchFilterDto {
    private String field;
    private String fieldName;
    private String value;
    private Date valueDate;
    private List<Select2Dto> listSelect;
    private List<Select2Dto> listSelectCondition;
    private boolean isChecked;
    private String type;
}
