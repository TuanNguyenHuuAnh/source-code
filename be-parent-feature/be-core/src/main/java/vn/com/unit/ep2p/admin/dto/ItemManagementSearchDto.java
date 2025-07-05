package vn.com.unit.ep2p.admin.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.dto.CommonSearchDto;

@Getter
@Setter
public class ItemManagementSearchDto extends CommonSearchDto {
    private String functionCode;

    private String functionName;

    private String description;

    private Boolean displayFlag;

    private Boolean actived;

    /** fieldValues */
    private String strFieldValues;

    public Boolean getDisplayFlag() {
        return displayFlag;
    }

    public Boolean getActived() {
        return actived;
    }

}
