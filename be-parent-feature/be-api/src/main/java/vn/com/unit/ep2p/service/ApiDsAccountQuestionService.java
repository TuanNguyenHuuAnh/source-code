package vn.com.unit.ep2p.service;

import java.util.List;

import vn.com.unit.ep2p.dto.req.AccountQuestionReq;
import vn.com.unit.ep2p.dto.req.QuestionForgotPasswordReq;
import vn.com.unit.ep2p.dto.res.QuestionForgotPasswordRes;

/**
 * @author TaiTM
 **/

public interface ApiDsAccountQuestionService {
    public List<QuestionForgotPasswordRes> getQuestionForgotPasswordRes(String username, Long companyId);

    public List<QuestionForgotPasswordRes> getQuestionForgotPasswordRes(Long userId) throws Exception;

    public void saveOrUpdateAccountQuestion(AccountQuestionReq accountQuestionReq) throws Exception;

    boolean checkQuestionMapping(QuestionForgotPasswordReq condition);

    boolean checkValidAgentQuestion(String agentCode, String questionCode, String answer);
}
