package vn.com.unit.cms.admin.all.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.ep2p.core.utils.Utility;
import vn.com.unit.cms.admin.all.dto.TermAddOrEditDto;
import vn.com.unit.cms.admin.all.dto.TermLanguageDto;
import vn.com.unit.cms.admin.all.dto.TermListDto;
import vn.com.unit.cms.admin.all.dto.TermSearchDto;
import vn.com.unit.cms.admin.all.entity.Term;
import vn.com.unit.cms.admin.all.entity.TermLanguage;
import vn.com.unit.cms.admin.all.enumdef.TermProcessEnum;
import vn.com.unit.cms.admin.all.enumdef.TermSearchEnum;
import vn.com.unit.cms.admin.all.enumdef.TermTypeEnum;
import vn.com.unit.cms.admin.all.repository.TermLanguageRepository;
import vn.com.unit.cms.admin.all.repository.TermRepository;
import vn.com.unit.cms.admin.all.service.TermService;
import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.core.config.SystemConfig;
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.ep2p.constant.UrlConst;

import vn.com.unit.core.service.LanguageService;


@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class TermServiceImpl implements TermService {

    @Autowired
    private TermRepository termRepository;

    @Autowired
    private TermLanguageRepository termLanguageRepository;
    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private LanguageService languageService;

    /**
     * Get the list of Term
     * 
     * @param currentPage
     *            requested page
     * @param modelAndView
     *            model and view
     * @param termSearchDto
     *            contains search criteria
     * @param locale
     *            contains language information
     * @return pageWrapper includes rendering items and paging information
     * 
     */
    @Override
    public PageWrapper<TermListDto> list(int currentPage, TermSearchDto termSearchDto) {

        // prepare the search criteria for before querying.
        setSearchCriteria(termSearchDto);
        if (termSearchDto.getLoanEnable() != null && termSearchDto.getDepositsEnable() != null) {
            termSearchDto.setDepositsEnable(null);
            termSearchDto.setLoanEnable(null);
        }
        
        int pageSize = termSearchDto.getPageSize() != null ? termSearchDto.getPageSize() : systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
        int count = termRepository.countBySearchCondition(termSearchDto);
        if ((count % pageSize == 0 && currentPage > count / pageSize) || (count % pageSize > 0 && currentPage - 1 > count / pageSize)) {
            currentPage = 1;
        }
        PageWrapper<TermListDto> pageWrapper = new PageWrapper<TermListDto>(currentPage, pageSize);
        
        List<TermListDto> termListDto = new ArrayList<>();
        if (count > 0) {
            int offsetSQL = Utility.calculateOffsetSQL(currentPage, pageSize);
            termListDto = termRepository.findBySearchCondition(offsetSQL, pageSize, termSearchDto);
        }

        pageWrapper.setDataAndCount(termListDto, count);

        return pageWrapper;
    }

    /**
     * fulfill search criteria to prepare for searching
     * 
     * @param termSearchDto
     *            contain search information
     */
    private void setSearchCriteria(TermSearchDto termSearchDto) {
        String searchKeyWord = termSearchDto.getSearchKeyWord();
        if (searchKeyWord != null)
            searchKeyWord = searchKeyWord.trim();

        if (null == termSearchDto.getSelectedField()) {
            termSearchDto.setSelectedField(new ArrayList<String>());
        }
        
        if (termSearchDto.getDepositsEnable() != null && termSearchDto.getLoanEnable() != null){
        	termSearchDto.setDepositsEnable(null);
        	termSearchDto.setLoanEnable(null);
        }
        
        if (termSearchDto.getSelectedField().isEmpty()) {
//            termSearchDto.setCode(searchKeyWord);
//            termSearchDto.setTitle(searchKeyWord);
//            termSearchDto.setDescription(searchKeyWord);
        } else {
            for (String field : termSearchDto.getSelectedField()) {
                if (StringUtils.equals(field, TermSearchEnum.CODE.name())) {
                    termSearchDto.setCode(searchKeyWord);
                    continue;
                }
                if (StringUtils.equals(field, TermSearchEnum.NAME.name())) {
                    termSearchDto.setTitle(searchKeyWord);
                    continue;
                }
                /*
                 * tungns 2017-05-24
                 */
                if (StringUtils.equals(field, TermSearchEnum.DESC.name())) {
                    termSearchDto.setDescription(searchKeyWord);
                    continue;
                }
                

                if (StringUtils.equals(field, "DEPOSITS")) {
                	termSearchDto.setDepositsEnable("1");
                    continue;
                }
                if (StringUtils.equals(field, "LOAN")) {
                	termSearchDto.setLoanEnable("0");
                    continue;
                }
                
            }
        }
    }

    /**
     * Returns information for a specific term
     * 
     * @param id
     * @param language
     * @return termEditDto object represents term data
     */
    @Override
    public TermAddOrEditDto get(Long id, String language) {
        Term term = termRepository.findOne(id);

        TermAddOrEditDto termAddOrEditDto = new TermAddOrEditDto();
        if (term != null) {
            termAddOrEditDto.setId(term.getId());
            termAddOrEditDto.setCode(term.getCode());
            termAddOrEditDto.setSortOrder(term.getSortOrder());
            termAddOrEditDto.setDescription(term.getDescription());
            termAddOrEditDto.setCreateDate(term.getCreateDate());
            termAddOrEditDto.setUpdateDate(term.getUpdateDate());
            termAddOrEditDto.setDeleteDate(term.getDeleteDate());
            termAddOrEditDto.setCreateBy(term.getCreateBy());
            termAddOrEditDto.setUpdateBy(term.getUpdateBy());
            termAddOrEditDto.setDeleteBy(term.getDeleteBy());
            termAddOrEditDto.setTermValue(term.getTermValue());
            termAddOrEditDto.setTermType(term.getTermType());
            termAddOrEditDto.setName(term.getName());

            // set termType
            for (TermTypeEnum termEnum : TermTypeEnum.values()) {
                if (StringUtils.equals(term.getTermType(), termEnum.toString())) {
                    termAddOrEditDto.setTermName(termEnum.getName());
                    break;
                }
            }
        }

        List<TermLanguageDto> termLanguageList = getTermLanguageList(id);
        termAddOrEditDto.setTermLanguageList(termLanguageList);

        String url = UrlConst.TERM.concat(UrlConst.DETAIL);
        if (null != id) {
            url = url.concat("?id=").concat(id.toString());
        }
        termAddOrEditDto.setUrl(url);

        return termAddOrEditDto;
    }

    /**
     * Retrieves term data for editing
     * 
     * @param modelAndView
     * @param termId
     *            id of term
     */
    @Override
    public TermAddOrEditDto getEdit(Long termId) {

        TermAddOrEditDto termAddOrEditDto = new TermAddOrEditDto();
        if (termId == null) {
            termAddOrEditDto.setStatus(TermProcessEnum.CREATE.getName());
            termAddOrEditDto.setSortOrder(termRepository.getMaxOrder() + 1);
            return termAddOrEditDto;
        }
        Term term = termRepository.findOne(termId);

        if (term != null) {
            termAddOrEditDto.setId(term.getId());
            termAddOrEditDto.setCode(term.getCode());
            termAddOrEditDto.setSortOrder(term.getSortOrder());
            termAddOrEditDto.setDescription(term.getDescription());

            String url = UrlConst.TERM.concat(UrlConst.EDIT);
            if (null != termId) {
                url = url.concat("?id=").concat(termId.toString());
            }
            termAddOrEditDto.setUrl(url);
            termAddOrEditDto.setTermValue(term.getTermValue());
            termAddOrEditDto.setTermType(term.getTermType());
            termAddOrEditDto.setName(term.getName());
            termAddOrEditDto.setLoanTerm(term.isLoanTerm());
            termAddOrEditDto.setStatus(term.getStatus());
            termAddOrEditDto.setComment(term.getTermComment());
            termAddOrEditDto.setApproveBy(term.getApproveBy());
            termAddOrEditDto.setApproveDate(term.getApproveDate());
            termAddOrEditDto.setPublishBy(term.getPublishBy());
            termAddOrEditDto.setPublishDate(term.getPublishDate());
            termAddOrEditDto.setUnitType(term.getUnitType());
            termAddOrEditDto.setFromValue(term.getFromValue());
            termAddOrEditDto.setToValue(term.getToValue());
            termAddOrEditDto.setCreateBy(term.getCreateBy());
            termAddOrEditDto.setCreateDate(term.getCreateDate());
        }
        List<TermLanguageDto> termLanguageList = getTermLanguageList(termId);
        termAddOrEditDto.setTermLanguageList(termLanguageList);
        return termAddOrEditDto;
    }

    /**
     * Initialize language list
     */
    @Override
    public void initLanguageList(ModelAndView modelAndView) {
        List<LanguageDto> languageList = languageService.getLanguageDtoList();

        modelAndView.addObject("languageList", languageList);

    }

    private List<TermLanguageDto> getTermLanguageList(Long id) {
        List<TermLanguage> termLanguageList = termLanguageRepository.findAllByTermId(id);
        List<TermLanguageDto> termLanguageDtoList = new ArrayList<>();
        for (TermLanguage item : termLanguageList) {
            TermLanguageDto termLanguageDto = new TermLanguageDto();
            termLanguageDto.setId(item.getId());
            termLanguageDto.setTermId(item.getTermId());
            termLanguageDto.setLanguageCode(item.getLanguageCode());
            termLanguageDto.setTitle(item.getTitle());
            termLanguageDtoList.add(termLanguageDto);
        }
        return termLanguageDtoList;
    }

    /**
     * Edit term
     * 
     * @param termEditDto
     *            object contains editing information
     */
    @Override
    @Transactional
    public void createOrEdit(TermAddOrEditDto termEditDto) {

        String userName = UserProfileUtils.getUserNameLogin();

        createOrEditTerm(termEditDto, userName);

        createOrEditTermLanguage(termEditDto, userName);
    }

    private void createOrEditTerm(TermAddOrEditDto termAddOrEditDto, String userName) {
        Term term = new Term();
        Long id = termAddOrEditDto.getId();
        if (id != null) {
            term = termRepository.findOne(id);

            if (term == null)
                throw new BusinessException("Not found Term with id " + termAddOrEditDto.getId());
            term.setUpdateDate(new Date());
            term.setUpdateBy(userName);
        } else {
            term.setCreateDate(new Date());
            term.setCreateBy(userName);
        }
        
        term.setCode(termAddOrEditDto.getCode());
        term.setDescription(termAddOrEditDto.getDescription());
        term.setSortOrder(termAddOrEditDto.getSortOrder());
        term.setDescription(termAddOrEditDto.getDescription());
        if(!termAddOrEditDto.getUnitType().equals("6")){
        	term.setTermValue(termAddOrEditDto.getTermValue());
        }   
        term.setTermType(termAddOrEditDto.getTermType());
        term.setName(termAddOrEditDto.getName());
        term.setLoanTerm(termAddOrEditDto.isLoanTerm());
        term.setStatus(termAddOrEditDto.getStatus());
        term.setTermComment(termAddOrEditDto.getComment());
        term.setUnitType(termAddOrEditDto.getUnitType());
        term.setFromValue(termAddOrEditDto.getFromValue());
        term.setToValue(termAddOrEditDto.getToValue());

        termRepository.save(term);

        termAddOrEditDto.setId(term.getId());

    }

    private void createOrEditTermLanguage(TermAddOrEditDto termAddOrEditDto, String userName) {
        for (TermLanguageDto item : termAddOrEditDto.getTermLanguageList()) {
            TermLanguage termLanguage = new TermLanguage();

            if (item.getId() != null) {
                termLanguage = termLanguageRepository.findOne(item.getId());
                if (termLanguage == null)
                    throw new BusinessException("Cannot found Term Language with id : " + item.getId());
                termLanguage.setUpdateDate(new Date());
                termLanguage.setUpdateBy(userName);
            } else {
                termLanguage.setCreateDate(new Date());
                termLanguage.setCreateBy(userName);
            }

            termLanguage.setLanguageCode(item.getLanguageCode());
            termLanguage.setTermId(termAddOrEditDto.getId());
            termLanguage.setTitle(item.getTitle());

            termLanguageRepository.save(termLanguage);
        }
    }

    /**
     * Get term by it's id
     * 
     * @param id
     *            term's id
     * @param languageCode
     * @return
     */
    @Override
    public Term getById(Long id, String languageCode) {
        return termRepository.getTerm(id, languageCode);
    }

    /**
     * Delete term based on its id
     * 
     * @param id
     *            term's id
     */
    @Override
    @Transactional
    public void delete(Long id) {
        Term term = termRepository.findOne(id);
        if (term == null)
            throw new BusinessException("Cannot find Term with id: " + id);
        String userName = UserProfileUtils.getUserNameLogin();

        deleteTermLanguage(id, userName);
        deleteTerm(term, userName);
    }

    private void deleteTermLanguage(Long id, String userName) {
        termLanguageRepository.deleteByTermId(id, new Date(), userName);

    }

    private void deleteTerm(Term term, String userName) {
        term.setDeleteDate(new Date());
        term.setDeleteBy(userName);
        termRepository.save(term);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.cms.admin.all.service.TermService#findByCode(java.lang.String)
     */
    @Override
    public Term findByCode(String code) {
        Term term = termRepository.findByCode(code);
        return term;
    }

    /**
     * findAllByLanguageCode
     *
     * @param languageCode
     * @return List<TermListDto>
     * @author hand
     */
    @Override
    public List<TermListDto> findAllByLanguageCode(String languageCode) {
        return termRepository.findAllByLanguageCode(languageCode);
    }

    /**
     * findByValueAndType
     *
     * @param termValue
     * @param termType
     * @return Term
     * @author hand
     */
    @Override
    public Term findByValueAndType(int termValue, String termType) {
        return termRepository.findByValueAndType(termValue, termType);
    }

    /** getMaxCode
    *
    * @author diennv
    * @return max code
    */
    @Override
    public String getMaxCode() {
        return termRepository.getMaxCode();
    }

	@Override
	public Term findByValueAndTypeAndUnitTypeAndLoanType(int termValue, String termType, String unitType, boolean loanTerm) {
		return termRepository.findByValueAndTypeAndUnitTypeAndLoanType(termValue, termType, unitType, loanTerm);
	}

	@Override
	public Term findByFromValueAndToValueAndUnitTypeAndTermTypeAndLoanTerm(long fromValue, long toValue, String unitType,
			String termType, boolean loanTerm) {
		return termRepository.findByFromValueAndToValueAndUnitTypeAndTermTypeAndLoanTerm(fromValue, toValue, unitType, termType, loanTerm);
	}

	
}
