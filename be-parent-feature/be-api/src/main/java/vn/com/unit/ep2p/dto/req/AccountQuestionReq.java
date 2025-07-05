package vn.com.unit.ep2p.dto.req;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.dto.res.QuestionForgotPasswordRes;

/**
 * @author TaiTM
 **/
@Getter
@Setter
public class AccountQuestionReq {
    @ApiModelProperty(notes = "Agent code", example = "100001", required = true, position = 0)
    private String agentCode;

    @ApiModelProperty(notes = "Question", example = "[{\"id\": 1,\"question\": \"Con vật bạn yêu thích ?\",\"answer\": \"Con mèo\"}, {\"id\": 2,  \"question\": \" Tên ba mẹ của bạn ?\", \"answer\": \"Trần Văn\"}, {\"id\": 3, \"question\": \" Trường tiểu học mà bạn đã học tên gì ?\",\"answer\": \"Nguyễn Văn Trỗi\"}]", required = true, position = 0)
    private List<QuestionForgotPasswordRes> questions;
}
