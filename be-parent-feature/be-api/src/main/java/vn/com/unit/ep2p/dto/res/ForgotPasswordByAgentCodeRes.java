package vn.com.unit.ep2p.dto.res;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * ForgotPasswordRes
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
public class ForgotPasswordByAgentCodeRes {
    private String otpTo;
    private String agentCode;
    private List<QuestionForgotPasswordRes> questions;
    private boolean agencyFlag;
}
