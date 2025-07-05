/*******************************************************************************
 * Class        ：EfoOzDocStatisticsDto
 * Created date ：2021/02/03
 * Lasted date  ：2021/02/03
 * Author       ：TaiTM
 * Change log   ：2021/02/03：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * EfoOzDocStatisticsDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Setter
@Getter
public class EfoOzDocStatisticsDto {
    private Long allDoc = 0L;

    private Long rejectedDoc = 0L;

    private Long finishedDoc = 0L;

    private Long draftDoc = 0L;

    private Map<String, Long> mapData = new HashMap<String, Long>();
    
    private List dataChart = new ArrayList<>();
    
    private List<Long> docList = new ArrayList<>();;

    private List<String> docListTitle = new ArrayList<>();

    private Long total;

    private int countStatus;
}
