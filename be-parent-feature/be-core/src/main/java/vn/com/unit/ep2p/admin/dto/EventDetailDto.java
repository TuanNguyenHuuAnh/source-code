package vn.com.unit.ep2p.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ApiModel
public class EventDetailDto {
    @ApiModelProperty(notes = "title of event", example = "lịch sinh nhật", required = false, position = 0)
    private String title;
    @ApiModelProperty(notes = "start time: milisecond", example = "1637735280000", required = false, position = 0)
    private Date start;
    @ApiModelProperty(notes = "end time: milisecond", example = "1637735280000", required = false, position = 0)
    private Date end;
    @ApiModelProperty(notes = "type of event, 1: event, 2: payment, 3: birthday", example = "1", required = false, position = 0)
    private String type;
    @ApiModelProperty(notes = "total event in that day", example = "23", required = false, position = 0)
    private int count;
}
