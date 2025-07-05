package vn.com.unit.ep2p.dto.req;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.dto.res.QuestionForgotPasswordRes;

/**
 * RenewPasswordReq
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
public class RenewPasswordReq {
    @ApiModelProperty(notes = "Old Password", example = "Abc!@123", required = false)
    private String oldPassword;
    @ApiModelProperty(notes = "New Password", example = "Abc!@123", required = true)
    private String newPassword;
    @ApiModelProperty(notes = "Confirm New Password", example = "Abc!@123", required = true)
    private String confirmNewPassword;

    @ApiModelProperty(notes = "OTP", example = "1234", required = false)
    private String otp;

    @ApiModelProperty(notes = "Agent code", example = "100001", required = true)
    private String agentCode;

    @ApiModelProperty(notes = "Question", example = "[{\"id\": 1,\"question\": \"Con vật bạn yêu thích ?\",\"answer\": \"Con mèo\"}, {\"id\": 2,  \"question\": \" Tên ba mẹ của bạn ?\", \"answer\": \"Trần Văn\"}, {\"id\": 3, \"question\": \" Trường tiểu học mà bạn đã học tên gì ?\",\"answer\": \"Nguyễn Văn Trỗi\"}]", required = false)
    private List<QuestionForgotPasswordRes> questions;
}
