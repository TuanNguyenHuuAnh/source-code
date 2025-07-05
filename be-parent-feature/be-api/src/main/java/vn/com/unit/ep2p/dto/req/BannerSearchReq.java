package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "Class properties to search banner")
@Getter
@Setter
public class BannerSearchReq extends CommonSearchReq {

    @ApiModelProperty(notes = "code", example = "bannerCode", required = false, position = 0)
    private String code;

    @ApiModelProperty(notes = "name", example = "bannerName", required = false, position = 0)
    private String name;

    @ApiModelProperty(notes = "mobileEnable", example = "bannerName", required = false, position = 0)
    private String mobileEnable;

    @ApiModelProperty(notes = "pcEnable", example = "bannerName", required = false, position = 0)
    private String pcEnable;

    @ApiModelProperty(notes = "bannerTop", example = "bannerName", required = false, position = 0)
    private String bannerTop;

    @ApiModelProperty(notes = "bannerMiddle", example = "bannerName", required = false, position = 0)
    private String bannerMiddle;

    @ApiModelProperty(notes = "status", example = "1", required = false, position = 0)
    private Integer status;

    @ApiModelProperty(notes = "statusName", example = "Lưu nháp", required = false, position = 0)
    private String statusName;
}
