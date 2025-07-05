package vn.com.unit.ep2p.dto.res;

import lombok.Getter;
import lombok.Setter;

/**
 * @author TaiTM
 */
@Getter
@Setter
public class QuestionForgotPasswordRes {
    private Long id;
    private String questionCode;
    private String question;
    private String answer;
}
