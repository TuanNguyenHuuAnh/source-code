package vn.com.unit.ep2p.dto.req;

import lombok.Getter;
import lombok.Setter;

/**
 * @author TaiTM
 */
@Getter
@Setter
public class QuestionForgotPasswordReq {
    private String agentCode;
    private String answer;
    private String questionCode;
}
