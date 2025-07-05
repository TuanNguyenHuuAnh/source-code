SELECT
	count(*)
FROM m_term term
LEFT JOIN m_term_language termLanguage ON (term.id = termLanguage.m_term_id AND termLanguage.delete_date is null)
WHERE
	term.delete_date is NULL
	AND	UPPER(termLanguage.m_language_code) = UPPER(/*termSearchDto.languageCode*/)
	/*IF termSearchDto.depositsEnable != null && termSearchDto.depositsEnable != ''*/
	AND term.is_loan_term = /*termSearchDto.depositsEnable*/
	/*END*/
	/*IF termSearchDto.loanEnable != null && termSearchDto.loanEnable != ''*/
	AND term.is_loan_term = /*termSearchDto.loanEnable*/
	/*END*/
	/*IF termSearchDto.code != null && termSearchDto.code != ''*/
	AND UPPER(term.code) LIKE UPPER(TRIM('%'||/*termSearchDto.code*/ ||'%'))
	/*END*/
	/*IF termSearchDto.title != null && termSearchDto.title != ''*/
	AND UPPER(termLanguage.title) LIKE UPPER(TRIM('%'||/*termSearchDto.title*/ ||'%'))
	/*END*/
	/*IF termSearchDto.status != null && termSearchDto.status != ''*/
	AND term.status = /*termSearchDto.status*/
	/*END*/
	