package vn.com.unit.ep2p.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.service.AccountService;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.ds.dto.DsAccountQuestionDto;
import vn.com.unit.ep2p.core.ds.entity.DsAccountQuestion;
import vn.com.unit.ep2p.core.ds.repository.DsAccountRepository;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.core.utils.NullAwareBeanUtils;
import vn.com.unit.ep2p.dto.req.AccountQuestionReq;
import vn.com.unit.ep2p.dto.req.QuestionForgotPasswordReq;
import vn.com.unit.ep2p.dto.res.QuestionForgotPasswordRes;
import vn.com.unit.ep2p.service.ApiDsAccountQuestionService;

/**
 * @author TaiTM
 **/
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class ApiDsAccountQuestionServiceImpl extends AbstractCommonService implements ApiDsAccountQuestionService {

	private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DsAccountRepository dsAccountRepository;
    
    @Autowired
    private AccountService accountService;

    @Override
    public List<QuestionForgotPasswordRes> getQuestionForgotPasswordRes(String username, Long companyId) {
        Long id = companyId;
        if (id == null) {
            id = UserProfileUtils.getCompanyId();
        }
        List<DsAccountQuestionDto> list = dsAccountRepository.findDsAccountQuestionDtoByUsername(username, id);
        List<QuestionForgotPasswordRes> res = new ArrayList<>();
        copyDataList(list, res);

        return res;
    }

    @Override
    public List<QuestionForgotPasswordRes> getQuestionForgotPasswordRes(Long userId) throws Exception{
        Long companyId = UserProfileUtils.getCompanyId();
        if (companyId == null) {
            throw new DetailException(AppApiExceptionCodeConstant.E4021203_APPAPI_COMPANY_NOT_FOUND);
        }
        
        List<DsAccountQuestionDto> list = dsAccountRepository.findDsAccountQuestionDtoByUserId(userId);
        List<QuestionForgotPasswordRes> res = new ArrayList<>();
        copyDataList(list, res);
        return res;
    }

    
    private void copyDataList(List<DsAccountQuestionDto> source, List<QuestionForgotPasswordRes> target) {
        if (target == null) {
            target = new ArrayList<QuestionForgotPasswordRes>();
        }
        if (CollectionUtils.isNotEmpty(source)) {
            for (DsAccountQuestionDto so : source) {
                QuestionForgotPasswordRes tar = new QuestionForgotPasswordRes();
                try {
                    NullAwareBeanUtils.copyPropertiesWONull(so, tar);
                } catch (Exception e) {
                    logger.error("Exception ", e);
                }
                target.add(tar);
            }
        }
    }

    @Override
    public void saveOrUpdateAccountQuestion(AccountQuestionReq accountQuestionReq) throws Exception {
        try {
            Long companyId = UserProfileUtils.getCompanyId();
            if (companyId == null) {
                throw new DetailException(AppApiExceptionCodeConstant.E4021203_APPAPI_COMPANY_NOT_FOUND);
            }

            JcaAccount account = accountService.findByUserName(getUsernameAction(), companyId);
            if (account == null) {
                throw new DetailException(AppApiExceptionCodeConstant.E402806_APPAPI_ACCOUNT_NOT_FOUND);
            }

            List<DsAccountQuestion> listSave = new ArrayList<>();

            dsAccountRepository.deleteByUsername(account.getUsername());
            for (QuestionForgotPasswordRes quest : accountQuestionReq.getQuestions()) {
                DsAccountQuestion data = new DsAccountQuestion();

                data.setUserId(account.getId());
                data.setUsername(account.getUsername());
                data.setCompanyId(companyId);

                data.setQuestion(quest.getQuestion());
                data.setQuestionCode(quest.getQuestionCode());
                data.setAnswer(quest.getAnswer());

                data.setCreatedDate(new Date());
                data.setCreatedId(account.getId());

                listSave.add(data);
            }


            if (CollectionUtils.isNotEmpty(listSave)) {
                dsAccountRepository.save(listSave);
            }
        } catch (Exception e) {
            logger.error("SAVE QUESTION", e);
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E402806_APPAPI_ACCOUNT_NOT_FOUND);
        }
    }

    @Override
    public boolean checkQuestionMapping(QuestionForgotPasswordReq condition) {
        //get list question from database by agent code
        List<QuestionForgotPasswordRes> questionsOfAgent = getQuestionForgotPasswordRes(condition.getAgentCode().toString(), 2L);
        //(question, answer)
        Map<String, String> questionAnswer =  questionsOfAgent
                .stream()
                .collect(Collectors.toMap(QuestionForgotPasswordRes::getQuestionCode, QuestionForgotPasswordRes::getAnswer));

        for (Map.Entry<String, String> entry : questionAnswer.entrySet()) {
            //check question is valid
            //check answer is valid
            if(entry.getKey().equalsIgnoreCase(condition.getQuestionCode()) && entry.getValue().equalsIgnoreCase(condition.getAnswer())){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkValidAgentQuestion(String agentCode, String questionCode, String answer) {
        return dsAccountRepository.countAgentQuestion(agentCode, questionCode, answer) == 0;
    }
}
