package vn.com.unit.cms.core.module.ga.dto.search;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class GrowthSearchGa extends CommonSearchWithPagingDto {

    private String agentCode;
    private String orgCode;
    private String agentGroup; //GA
    private String month;
    private String year;
    private String quarter;
    private String type;            //MTD, QTD ,YTD

}
