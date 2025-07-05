package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;
//import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.dto.TermListDto;
import vn.com.unit.cms.admin.all.dto.TermSearchDto;
import vn.com.unit.cms.admin.all.entity.Term;
import vn.com.unit.db.repository.DbRepository;

public interface TermRepository extends DbRepository<Term, Long> {

	public int countBySearchCondition(@Param("termSearchDto") TermSearchDto termSearchDto);

	public List<TermListDto> findBySearchCondition(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage,
			@Param("termSearchDto") TermSearchDto termSearchDto);

	public Term getTerm(@Param("id") Long id, @Param("language") String language);

	public List<Term> getSortedTermList();

	/*
	 * tungns 2017-05-24
	 */
	public Term findByCode(@Param("code") String code);

	/*
	 * tungns 2017-05-24
	 */
	public int getMaxOrder();

	/**
	 * findAllByLanguageCode
	 *
	 * @param languageCode
	 * @return List<TermListDto>
	 * @author hand
	 */
	public List<TermListDto> findAllByLanguageCode(@Param("languageCode") String languageCode);

	/**
	 * findByValueAndType
	 *
	 * @param termValue
	 * @param termType
	 * @return Term
	 * @author hand
	 */
	public Term findByValueAndType(@Param("termValue") int termValue, @Param("termType") String termType);

	/**
	 * getMaxCode
	 *
	 * @author diennv
	 */
	String getMaxCode();

	/**
	 * findByValueAndTypeAndUnitTypeAndLoanType
	 * 
	 * @Param termValue
	 * @Param termType
	 * @Param unitType
	 * @Param loanTerm
	 * @author diennv
	 */
	public Term findByValueAndTypeAndUnitTypeAndLoanType(@Param("termValue") int termValue,
			@Param("termType") String termType, @Param("unitType") String unitType,
			@Param("loanTerm") boolean loanTerm);

	/**
	 * findByFromValueAndToValueAndUnitTypeAndTermTypeAndLoanTerm
	 * 
	 * @Param loanTerm
	 * @Param loanTerm
	 * @Param loanTerm
	 * @Param loanTerm
	 * @Param loanTerm
	 * @author diennv
	 */
	public Term findByFromValueAndToValueAndUnitTypeAndTermTypeAndLoanTerm(@Param("fromValue") long fromValue,
			@Param("toValue") long toValue, @Param("unitType") String unitType, @Param("termType") String termType,
			@Param("loanTerm") boolean loanTerm);

}
