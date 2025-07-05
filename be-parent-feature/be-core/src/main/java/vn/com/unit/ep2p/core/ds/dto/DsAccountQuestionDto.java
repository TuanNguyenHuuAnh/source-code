package vn.com.unit.ep2p.core.ds.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * @author TaiTM
 **/
@Getter
@Setter
public class DsAccountQuestionDto extends AbstractTracking {
    private String username;

    private Long userId;

    private String questionCode;

    private String question;
    
    private String answer;

    private Long companyId;
}
