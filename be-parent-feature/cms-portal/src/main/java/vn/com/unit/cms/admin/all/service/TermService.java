package vn.com.unit.cms.admin.all.service;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.dto.TermAddOrEditDto;
import vn.com.unit.cms.admin.all.dto.TermListDto;
import vn.com.unit.cms.admin.all.dto.TermSearchDto;
import vn.com.unit.cms.admin.all.entity.Term;
import vn.com.unit.common.dto.PageWrapper;

public interface TermService {
	public PageWrapper<TermListDto> list(int page, TermSearchDto termSearchDto);

	public TermAddOrEditDto get(Long id, String string);
	
	public Term getById(Long id, String string);

	public TermAddOrEditDto getEdit(Long termId);
	
	public void createOrEdit(TermAddOrEditDto termEditDto);

	void initLanguageList(ModelAndView modelAndView);

	public void delete(Long id);

    /** findByCode
     *
     * @param code
     * @return
     * @author tungns <tungns@unit.com.vn>
     */
    public Term findByCode(String code);
    
    /**
     * findAllByLanguageCode
     *
     * @param languageCode
     * @return List<TermListDto>
     * @author hand
     */
    public List<TermListDto> findAllByLanguageCode(String languageCode);
    
    /**
     * findByValueAndType
     *
     * @param termValue
     * @param termType
     * @return Term
     * @author hand
     */
    public Term findByValueAndType(int termValue, String termType);
    
    /** getMaxCode
    *
    * @author diennv
    * @return max code
    */
    String getMaxCode();
        
    /** findByValueAndTypeAndUnitTypeAndLoanType
    *
    * @author diennv
    * @return Term
    */
    public Term findByValueAndTypeAndUnitTypeAndLoanType(int termValue,  String termType, String unitType, boolean loanTerm);
    
    /** findByFromValueAndToValueAndUnitTypeAndTermTypeAndLoanTerm
    *
    * @author diennv
    * @return Term
    */
    public Term findByFromValueAndToValueAndUnitTypeAndTermTypeAndLoanTerm(long fromValue, long toValue, String unitType, String termType, boolean loanTerm);
}
