package vn.com.unit.ep2p.dto.req;

import lombok.Getter;
import lombok.Setter;

/**
 * @author khadm
 *
 */
@Getter
@Setter
public class SlaConfigReq {

    private Long id;

    private Long orgId;

    private Long calendarTypeId;

    private String slaType;

    private Long slaDueTime;

    private String slaTimeType;

    private String calendarOption;

    private Long activeFlag;

    private Long displayOrder;
}
