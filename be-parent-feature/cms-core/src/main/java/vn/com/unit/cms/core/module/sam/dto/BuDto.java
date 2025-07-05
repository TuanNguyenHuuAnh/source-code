package vn.com.unit.cms.core.module.sam.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuDto {

    private String bu;
    
    private List<ActivitiesDto> activityLst = new ArrayList<>();
}
